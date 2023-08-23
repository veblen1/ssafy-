package com.example.project

import com.example.project.viewmodels.BiometricViewModel
import com.example.project.viewmodels.AuthenticationState

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import com.mrhwsn.composelock.ComposeLock
import com.mrhwsn.composelock.ComposeLockCallback
import com.mrhwsn.composelock.Dot


@Composable
fun LoginPage(navController: NavHostController, viewModel: BiometricViewModel) {
    // 생체 인증 상태를 관찰
    val authState by viewModel.authenticationState.observeAsState()
    val context = LocalContext.current
    val fragmentActivity = context as? FragmentActivity

    when (authState) {
        is AuthenticationState.SUCCESS -> {
            navController.navigate("Home")
        }
        is AuthenticationState.ERROR -> {
            // 오류 메시지를 사용자에게 표시 (예: Toast, Snackbar 등)
        }
        is AuthenticationState.FAILURE -> {
            // 인증이 실패했을 때의 처리 (예: Toast, Snackbar 등으로 사용자에게 알리기)
        }
        null -> {
            // 아직 데이터가 없거나 초기 상태를 다루는 로직 (필요하다면)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 패턴 인증 영역
        ComposeLock(
            modifier = Modifier
                .width(400.dp)  // 폭을 300.dp로 설정
                .height(400.dp)  // 높이를 300.dp로 설정
                .fillMaxWidth(),
            dimension = 3,
            sensitivity = 100f,
            dotsColor = Color.Black,
            dotsSize = 20f,
            linesColor = Color.Black,
            linesStroke = 30f,
            animationDuration = 200,
            animationDelay = 100,
            callback = object : ComposeLockCallback {
                override fun onStart(dot: Dot) {
                    // 패턴 시작 시 액션
                }

                override fun onDotConnected(dot: Dot) {
                    // 패턴 입력 중 점 연결 시 액션
                }

                override fun onResult(result: List<Dot>) {
                    // 패턴 결과 받았을 때의 액션
                    // 여기서 패턴을 검증하고, 맞다면 로그인을 처리합니다.
                    // 예시: viewModel.verifyPattern(result)
                }
            }
        )


        Button(onClick = {
            // 클릭 시 Home으로 이동 , 지금은 임시로. 나중에 삭제예정
            navController.navigate("Home")
        }) {
            Text("로그인임시")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // 클릭 시 SignUp으로 이동
            navController.navigate("SignUp")
        }) {
            Text("회원가입임시")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // 클릭 시 생체 인증 시작
            showBiometricPrompt(fragmentActivity, viewModel)
        }) {
            Text("지문인식 임시 세팅")
        }

        Spacer(modifier = Modifier.height(16.dp))



    }
}

fun showBiometricPrompt(activity: FragmentActivity?, viewModel: BiometricViewModel) {
    activity?.let {
        val fragmentManager = it.supportFragmentManager
        val dialog = BiometricPromptDialogFragment()
        dialog.show(fragmentManager, "biometricDialog")
    }
}

// 패턴 라이브러리 git 주소 https://github.com/ThereWasLuna/ComposeLock