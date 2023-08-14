package com.ec25p5e.notesapp.feature_auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.GradientButton
import com.ec25p5e.notesapp.core.presentation.components.StandardTextFieldState
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.core.presentation.util.UiEvent
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.core.util.Screen
import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val state = viewModel.loginState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                is UiEvent.OnLogin -> {
                    onLogin()
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
                .align(Alignment.Center),
        ) {

            LottieView(
                json = R.raw.sign_in,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),

            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(id = R.string.sign_in_btn_text),
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
                    maxLength = Constants.MAX_USERNAME_LENGTH,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EnteredEmail(it))
                    },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    error = when(emailState.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                        is AuthError.InputTooShort -> stringResource(id = R.string.input_too_short_text)
                        is AuthError.InvalidEmail -> stringResource(id = R.string.invalid_email_text)
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
                        viewModel.onEvent(LoginEvent.EnteredPassword(it))
                    },
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    isPasswordVisible = passwordState.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                    },
                    error = when(passwordState.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.field_empty_text_error)
                        is AuthError.InputTooShort -> stringResource(id = R.string.input_too_short_text)
                        else -> ""
                    }
                )

                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp

                Spacer(modifier = Modifier.padding(10.dp))
                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = stringResource(id = R.string.sign_in_btn_text),
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),
                    onClick = {
                        viewModel.onEvent(LoginEvent.Login)
                    },
                    enabled = !state.isLoading
                )

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = {

                }) {
                    Text(
                        text = stringResource(id = R.string.create_account_button),
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }


                Spacer(modifier = Modifier.padding(5.dp))
                TextButton(onClick = {

                }) {
                    Text(
                        text = stringResource(id = R.string.reset_password_button),
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}