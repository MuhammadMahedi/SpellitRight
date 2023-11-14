package com.example.spellitright.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.spellitright.R
import com.example.spellitright.activities.MainActivity
import com.example.spellitright.data.listOfWords
import com.example.spellitright.databinding.FragmentHomeBinding
import com.example.spellitright.viewModels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var word:CharArray
    private val viewModel : HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.shuffledWord.observe(viewLifecycleOwner){
            binding.tvShuffledWord.text= it
        }

        viewModel.score.observe(viewLifecycleOwner){
            binding.tvScore.text = it.toString()
        }




        //binding.tvShuffledWord.text= viewModel.shuffledWord.value


        binding.btnDone.setOnClickListener {

            if(binding.etInput.text.toString().isNotEmpty()){

                val input=binding.etInput.text.toString().trim()

                if(viewModel.isCorrect(input)){
                    Toast.makeText(requireContext(), "Matched", Toast.LENGTH_SHORT).show()
                    binding.etInput.text = null
                    viewModel.getWord()
                    viewModel.incScore()
                }
                else{
                    Toast.makeText(requireContext(), "Did Not Matched",
                        Toast.LENGTH_SHORT).show()
                    showGameOverDialog()
                }

            }else Toast.makeText(requireContext(), "Please Enter something", Toast.LENGTH_SHORT).show()


        }

        binding.btnQuit.setOnClickListener {

            showExitDialog()

        }


        binding.btnRestart.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are You Sure?")
            builder.setMessage("You want to restart the game!!")
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.resetItAll()
            }
            builder.setNegativeButton("No"){ dialog, which ->
                dialog.dismiss()
            }
            // Create and show the AlertDialog
            val alertDialog = builder.create()
            alertDialog.show()
        }



    }






    private fun showGameOverDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Oops that's incorrect")
        builder.setMessage("the ans is ${viewModel.currentWord.value}")
        builder.setPositiveButton("Restart") { dialog, _ ->
            viewModel.resetItAll()
            dialog.dismiss()
        }
        builder.setNegativeButton("Quit") { dialog, _ ->
            showExitDialog()
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are You Sure?")
        builder.setMessage("You want to quit this game and Exit!!")
        builder.setPositiveButton("Yes") { _, _ ->
            activity?.finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }


}