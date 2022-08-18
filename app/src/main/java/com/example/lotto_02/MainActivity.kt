package com.example.lotto_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    // Mark -Properties/////////////////////////////////////////
    private val clearBtn: Button by lazy {
        findViewById<Button>(R.id.clearBtn)
    }
    private val addBtn: Button by lazy {
        findViewById(R.id.addBtn)
    }
    private val runBtn: Button by lazy {
        findViewById(R.id.runBtn)
    }
    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPickerNp)
    }
    private var didRun = false
    private val pickedNumSet = hashSetOf<Int>()
    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.generated_Num0_Tv),
            findViewById(R.id.generated_Num1_Tv),
            findViewById(R.id.generated_Num2_Tv),
            findViewById(R.id.generated_Num3_Tv),
            findViewById(R.id.generated_Num4_Tv),
            findViewById(R.id.generated_Num5_Tv)

        )
    }


    // Mark -LifeCycle/////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45
        initRunBtn()
        initAddBtn()
        initClearBtn()
    }


    // Mark -Run Button/////////////////////////////////////////
    private fun initRunBtn() {
        runBtn.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, num ->
                val textView = numberTextViewList[index]

                textView.text = num.toString()
                textView.isVisible = true
                setNumBackground(num, textView)
            }

            Log.d("MainActivity", list.toString())


        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if(pickedNumSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()
        val newList = pickedNumSet.toList() + numberList.subList(0, 6- pickedNumSet.size)
//        newList.sort  // sort은 어떤 collection 일 때만 가능한 건가? sorted 은 되는데 sort 은 안댐.
        return newList.sorted()
    }


    // Mark -Add Button/////////////////////////////////////////
    private fun initAddBtn() {
        addBtn.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후 시도해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickedNumSet.size >= 6) {
                Toast.makeText(this, "번호는 여섯개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickedNumSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다..", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewList[pickedNumSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            pickedNumSet.add(numberPicker.value)

            // can set backgroundColor in kotlin file
//            textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            setNumBackground(numberPicker.value, textView)


        }
    }

    // Mark -Clear Button/////////////////////////////////////////
    private fun initClearBtn() {
        clearBtn.setOnClickListener {
            numberTextViewList.forEach {
                it.isVisible = false
            }
            pickedNumSet.clear()

            didRun = false
        }
    }

    private fun setNumBackground(number: Int, textView: TextView){
        when(number){
                in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
                in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
                in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
                in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
                else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
            }

    }
}