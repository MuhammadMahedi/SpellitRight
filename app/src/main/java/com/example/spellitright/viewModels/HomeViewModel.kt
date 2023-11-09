package com.example.spellitright.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var listOfWords: MutableList<String> = mutableListOf()
    private var _currentWord = MutableLiveData<String>()
    val currentWord: LiveData<String> = _currentWord
    private var _shuffledWord = MutableLiveData<String>()
    val shuffledWord:LiveData<String> get()=_shuffledWord
    private var _score = MutableLiveData<Int>()
    val score:LiveData<Int> get() = _score

    init {
        getWord()
        _score.postValue(0)
    }

    fun getWord(){
        val word = com.example.spellitright.data.listOfWords.random().toUpperCase()
        _currentWord.postValue(word)

        if(listOfWords.contains(word)){
            getWord()
        }
        val tWord= word.toCharArray()
        tWord.shuffle()

        while(tWord.contentEquals(word.toCharArray())){
            tWord.shuffle()
        }
        _shuffledWord.postValue(String(tWord))
    }

    fun isCorrect(input:String):Boolean{
        return if(input.equals(_currentWord.value,true)){
            listOfWords.add(_currentWord.value!!)
            true
        } else false
    }

    fun incScore(){
        _score.value = (_score.value)?.plus(1)
    }

    fun resetItAll(){
        getWord()
        _score.postValue(0)
        listOfWords.clear()

    }


}