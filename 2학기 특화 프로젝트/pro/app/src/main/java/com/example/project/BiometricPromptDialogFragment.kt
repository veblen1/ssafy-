package com.example.project

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.project.viewmodels.BiometricViewModel
import com.example.project.viewmodels.AuthenticationState

class BiometricPromptDialogFragment : DialogFragment() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val viewModel: BiometricViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initBiometricPrompt()
        authenticate()
        return super.onCreateDialog(savedInstanceState)
    }

    private fun initBiometricPrompt() {
        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()), object :
            BiometricPrompt.AuthenticationCallback() {

            // 사용자가 스마트폰에 지문을 설정하지 않았을때 오류 도출
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                viewModel.setAuthenticationState(AuthenticationState.ERROR(errString.toString()))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                viewModel.setAuthenticationState(AuthenticationState.SUCCESS)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                viewModel.setAuthenticationState(AuthenticationState.FAILURE)
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
    }

    private fun authenticate() {
        biometricPrompt.authenticate(promptInfo)
    }
}