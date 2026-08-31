package com.demo.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operator by remember { mutableStateOf<String?>(null) }
    var clearOnNext by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = display,
            fontSize = 48.sp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )
        for (row in buttons) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (btn in row) {
                    Button(
                        onClick = {
                            when (btn) {
                                "C" -> {
                                    display = "0"
                                    operand1 = null
                                    operator = null
                                }
                                "+", "-", "*", "/" -> {
                                    operand1 = display.toDoubleOrNull()
                                    operator = btn
                                    clearOnNext = true
                                }
                                "=" -> {
                                    val op2 = display.toDoubleOrNull()
                                    if (operand1 != null && op2 != null && operator != null) {
                                        val res = when (operator) {
                                            "+" -> operand1!! + op2
                                            "-" -> operand1!! - op2
                                            "*" -> operand1!! * op2
                                            "/" -> if (op2 != 0.0) operand1!! / op2 else Double.NaN
                                            else -> op2
                                        }
                                        display = if (res.isNaN()) "Error" else if (res % 1.0 == 0.0) res.toLong().toString() else res.toString()
                                        operand1 = null
                                        operator = null
                                        clearOnNext = true
                                    }
                                }
                                else -> {
                                    if (display == "0" || clearOnNext) {
                                        display = btn
                                        clearOnNext = false
                                    } else {
                                        display += btn
                                    }
                                }
                            }
                        },
                        modifier = Modifier.weight(1f).padding(vertical = 4.dp).height(64.dp)
                    ) {
                        Text(text = btn, fontSize = 22.sp)
                    }
                }
            }
        }
    }
}