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
import com.example.spellitright.data.PreferenceHelper
import com.example.spellitright.data.listOfWords
import com.example.spellitright.databinding.FragmentHomeBinding
import com.example.spellitright.viewModels.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var word:CharArray
    private val viewModel : HomeViewModel by viewModels()
    private lateinit var preferencesHelper: PreferenceHelper
    private var currentHighScore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesHelper = PreferenceHelper(requireContext())
        currentHighScore = preferencesHelper.getHighScore()
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
                    viewModel.incScore()
                    if(!viewModel.allWordsSpelled()){
                        binding.etInput.text = null
                        viewModel.getWord()
                    }else{
                        preferencesHelper.setHighScore(viewModel.score.value!!)
                        showFinishDialog()
                    }

                }
                else{
                    Toast.makeText(requireContext(), "Did Not Matched",
                        Toast.LENGTH_SHORT).show()
                    if(viewModel.score.value!! > preferencesHelper.getHighScore()){
                        preferencesHelper.setHighScore(viewModel.score.value!!)
                    }
                    showGameOverDialog()
                }

            }else Toast.makeText(requireContext(), "Please Enter something", Toast.LENGTH_SHORT).show()


        }

        binding.btnQuit.setOnClickListener {

            showExitDialog()

        }


        binding.btnRestart.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are You Sure, you want to restart?")
            builder.setMessage("All the current scores will be gone")
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.resetItAll()
                binding.etInput.text = null
                dialog.dismiss()
            }
            builder.setNegativeButton("No"){ dialog, which ->
                dialog.dismiss()
            }
            builder.setCancelable(false)

            // Create and show the AlertDialog
            val alertDialog = builder.create()
            alertDialog.show()
        }



    }






    private fun showGameOverDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Oops!! that's incorrect")
        builder.setMessage("the ans is ${viewModel.currentWord.value}\n\n" +
                "Your Score : ${viewModel.score.value}\n" +
                "High Score : ${preferencesHelper.getHighScore()}")
        builder.setPositiveButton("Restart") { dialog, _ ->
            viewModel.resetItAll()
            binding.etInput.text = null
            dialog.dismiss()
        }
        builder.setNegativeButton("Quit") { dialog, _ ->
            activity?.finish()
            dialog.dismiss()
        }
        builder.setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are You Sure, You want to quit?")
        builder.setMessage("You will lose your points")
        builder.setPositiveButton("Yes") { _, _ ->
            activity?.finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showFinishDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Congratulations!!")
        builder.setMessage("You have spelled all the words correctly.")
        builder.setPositiveButton("Exit") { _, _ ->
            activity?.finish()
        }
        builder.setNegativeButton("Start Again") { dialog, _ ->
            viewModel.resetItAll()
            binding.etInput.text = null
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }


}