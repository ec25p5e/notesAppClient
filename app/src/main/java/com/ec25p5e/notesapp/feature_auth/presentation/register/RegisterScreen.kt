package com.ec25p5e.notesapp.feature_auth.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.GradientButton
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.login.LoginEvent
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    scaffoldState: SnackbarHostState,
    onPopBackStack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val usernameState = viewModel.usernameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val registerState = viewModel.registerState.value

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.onRegister.collect {
            onPopBackStack()
        }
    }

    LaunchedEffect(key1 = keyboardController) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    keyboardController?.hide()
                    scaffoldState.showSnackbar(
                        event.uiText.toString(),
                        duration = SnackbarDuration.Long
                    )
                }
                else -> {
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )
                .align(Alignment.BottomCenter),
        ) {

            Image(
                painter = painterResource(id = R.drawable.user_reg),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),

                )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(id = R.string.sign_up_btn_text),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(8.dp))
                StandardTextFieldState(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = emailState.text,
                    label = stringResource(id = R.string.label_email_input),
                    maxLength = Constants.MAX_EMAIL_LENGTH,
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnteredEmail(it))
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    error = when(emailState.error) {
                        is AuthError.FieldEmpty -> {
                            stringResource(id = R.string.field_empty_text_error)
                        }
                        is AuthError.InvalidEmail -> {
                            stringResource(id = R.string.invalid_email_text)
                        }
                        else -> ""
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                StandardTextFieldState(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = usernameState.text,
                    label = stringResource(id = R.string.label_username_input),
                    maxLength = Constants.MAX_USERNAME_LENGTH,
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnteredUsername(it))
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    error = when(usernameState.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                        else -> ""
                    }
                )

                Spacer(modifier = Modifier.padding(3.dp))
                StandardTextFieldState(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = passwordState.text,
                    label = stringResource(id = R.string.label_password_input),
                    maxLength = Constants.MAX_PASSWORD_LENGTH,
                    onValueChange = {
                        viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                    },
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    isPasswordVisible = passwordState.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)
                    },
                    error = when(passwordState.error) {
                        is AuthError.FieldEmpty -> {
                            stringResource(id = R.string.field_empty_text_error)
                        }
                        is AuthError.InputTooShort -> {
                            stringResource(id = R.string.min_pass_lenght, Constants.MIN_PASSWORD_LENGTH)
                        }
                        is AuthError.InvalidPassword -> {
                            stringResource(id = R.string.regexp_password_format_invalid)
                        }
                        else -> ""
                    }
                )

                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp


                Spacer(modifier = Modifier.padding(10.dp))
                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = stringResource(id = R.string.sign_up_btn_text),
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),
                    onClick = {
                        viewModel.onEvent(RegisterEvent.Register)
                    },
                    enabled = !registerState.isLoading,
                )

                if(registerState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = {
                    navController.popBackStack()
                }) {
                    Text(
                        text = stringResource(id = R.string.have_an_account_button),
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}