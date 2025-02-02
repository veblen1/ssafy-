package com.example.project

import SelectButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project.api.IdPwLoginRequest
import com.example.project.ui.theme.BrandColor1
import com.example.project.viewmodels.FireBaseViewModel
import com.example.project.viewmodels.ResponseState
import com.example.project.viewmodels.TextloginViewModel

@Composable
fun TextLoginPage(navController: NavHostController) {
    val textLoginModel: TextloginViewModel = hiltViewModel()
    val fireBaseViewModel: FireBaseViewModel = hiltViewModel()
    var loginText by remember { mutableStateOf(TextFieldValue("")) }
    var passwordText by remember { mutableStateOf(TextFieldValue("")) }
    var trigger by remember { mutableStateOf(0) }
    // 상태 확인
    val error by textLoginModel.error.collectAsState()
    val result by textLoginModel.loginState.collectAsState()

//    val ErrorState by textLoginModel.checkError.collectAsState()
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(result){
        if(result==1){
            textLoginModel.initLoginState()
            fireBaseViewModel.getFirebaseToken()
            navController.navigate("Home")
        }else if(result==2){
            showConfirmDialog=true
            textLoginModel.initLoginState()
        }
    }

    val onLoginClick = {
        val request = IdPwLoginRequest(
            userId = loginText.text,
            userPassword = passwordText.text
        )
        textLoginModel.loginWithIdPw(request)
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(13.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(300.dp, 200.dp)
            )

            if (showConfirmDialog) {
                val errormessage=error!!
                AlertDialog(onDismissRequest = {
                    showConfirmDialog = false
                }, title = {
                    Text(text = "로그인이 실패하였습니다.")
                }, text = {
                    Text(errormessage, fontSize = 18.sp)
                }, dismissButton = {
                    SelectButton(text = "네", onClick = {
                        showConfirmDialog = false
                    })
                }, confirmButton = {
                })
            }

            //아이디 입력창
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = "Email",
                    modifier = Modifier.size(30.dp),
                    tint = Color(0xFF1F59B6)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)  // Fill remaining space
                        .size(300.dp, 70.dp)
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = loginText,
                        onValueChange = { loginText = it },
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 23.sp),
                        modifier = Modifier
                            .size(300.dp, 70.dp),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),

                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                            .align(Alignment.BottomStart)
                    )
                }
            }

            // 비밀번호 입력창
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "Lock",
                    modifier = Modifier.size(30.dp),
                    tint = Color(0xFF1F59B6)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)  // Fill remaining space
                        .size(300.dp, 70.dp)
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        visualTransformation = PasswordVisualTransformation(),
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 23.sp),
                        modifier = Modifier
                            .size(300.dp, 70.dp),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                    )

                    // Spacer to simulate the bottom border
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                            .align(Alignment.BottomStart)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(end = 16.dp)
            ) {
                Spacer(modifier = Modifier.weight(0.2f))
                Text(text = "아이디", color = Color(0xFF1F59B6), modifier = Modifier.clickable {
                    navController.navigate("com.example.project.FindIdPage")
                })
                Text(
                    text = " / ", color = Color(0xFF1F59B6)
                )
                Text(text = "비밀번호", color = Color(0xFF1F59B6), modifier = Modifier.clickable {
                    navController.navigate("FindPwPage")
                })
                Text(
                    text = " 찾기", color = Color(0xFF1F59B6) // Here, set the color
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = onLoginClick,
                Modifier.size(181.dp, 45.dp),
                colors = ButtonDefaults.buttonColors(BrandColor1)
            ) {
                Text("로그인", fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("Login") },
                Modifier.size(181.dp, 45.dp),
                colors = ButtonDefaults.buttonColors(BrandColor1)
            ) {
                Text("패턴 로그인", fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("SignUp") },
                Modifier.size(181.dp, 45.dp),
                colors = ButtonDefaults.buttonColors(BrandColor1)
            ) {
                Text("회원가입", fontSize = 22.sp)
            }

        }
    }
}
