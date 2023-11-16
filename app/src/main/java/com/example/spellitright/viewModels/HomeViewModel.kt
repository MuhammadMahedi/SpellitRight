package com.example.spellitright.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spellitright.data.listOfWords

class HomeViewModel : ViewModel() {

    private var spelledWords: MutableList<String> = mutableListOf()
    private var _currentWord = MutableLiveData<String>()
    val currentWord: LiveData<String> = _currentWord
    private var _shuffledWord = MutableLiveData<String>()
    val shuffledWord:LiveData<String> get()=_shuffledWord
    private var _score = MutableLiveData<Int>()
    val score:LiveData<Int> get() = _score

    private var _highScore = MutableLiveData<Int>()
    val highScore:LiveData<Int> get() = _highScore


    init {
        getWord()
        _score.postValue(0)
    }

    fun getWord(){
        var word = com.example.spellitright.data.listOfWords.random().toUpperCase()

        while(spelledWords.contains(word)){
            word = com.example.spellitright.data.listOfWords.random().toUpperCase()
        }
        _currentWord.postValue(word)
        val tWord= word.toCharArray()
        tWord.shuffle()

        while(tWord.contentEquals(word.toCharArray())){
            tWord.shuffle()
        }
        _shuffledWord.postValue(String(tWord))
        //listOfWords.add(_currentWord.value!!)
    }

    fun isCorrect(input:String):Boolean{
        return if(input.equals(_currentWord.value,true)){
           spelledWords.add(_currentWord.value!!)
            true
        } else false
    }

    fun allWordsSpelled():Boolean{
        return spelledWords.size == listOfWords.size
    }
    fun incScore(){
        _score.value = (_score.value)?.plus(1)
    }

    fun resetItAll(){
        _score.postValue(0)
        spelledWords.clear()
        getWord()

    }


}