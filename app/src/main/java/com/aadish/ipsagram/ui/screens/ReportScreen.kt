package com.aadish.ipsagram.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.grey
import com.aadish.ipsagram.ui.theme.red
import com.aadish.ipsagram.ui.theme.white

val REPORT_TYPE_LIST = listOf(
    "Spam",
    "Nudity or sexual activity",
    "False information",
    "Sale of illegal or regulated goods",
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    docId: String,
    navController: NavHostController,
    onSubmitReport: (Context, String, String) -> Unit
) {
    var selectedReportType by remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .background(darkBackground),
        topBar = {
            TopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = darkBackground,
                    titleContentColor = white,
                ),
                title = {
                    Text(
                        text = "Report",
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = white
                        )
                    }
                },
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
                .padding(innerPadding)
        ) {
            Text(
                text = "Please identify reason(s) for the report.",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = white
                ),
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        top = 30.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                REPORT_TYPE_LIST.forEach { reportType ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clickable(onClick = { selectedReportType = reportType }),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = selectedReportType == reportType,
                            onClick = { selectedReportType = reportType },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = red
                            )
                        )
                        Text(
                            text = reportType,
                            color = white,
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onSubmitReport(context, docId, selectedReportType)
                    navController.navigateUp()
                },
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .width(300.dp)
                    .height(48.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = red,
                    disabledContainerColor = grey
                ),
                enabled = selectedReportType.isNotEmpty(),
            ) {
                Text(
                    text = "Submit",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W600,
                    color = white,
                )
            }
        }
    }
}