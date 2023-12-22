//package com.example.jelajahiapp.ui.screen.community
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.jelajahiapp.R
//import com.example.jelajahiapp.data.Result
//import com.example.jelajahiapp.data.ViewModelFactory
//import com.example.jelajahiapp.data.response.ResponseCommunity
//import com.example.jelajahiapp.data.response.ResponseUser
//import com.example.jelajahiapp.navigation.Screen
//import com.example.jelajahiapp.ui.screen.community.viewmodel.CommunityViewModel
//import com.example.jelajahiapp.ui.theme.fonts
//import com.example.jelajahiapp.ui.theme.purple100
//import com.example.jelajahiapp.ui.theme.white100
//
//@Composable
//
//fun EditCommunityScreen(
//    navController: NavHostController,
//    communityPost: ResponseCommunity,
//    viewModel: CommunityViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
//){
//    val onCommunityEditSubmitted: (Long, String, String, String) -> Unit = { id, placeName, location, description ->
//        viewModel.editCommunityPost(id, placeName, location, description)
//    }
//
//    EditCommunityContent(
//        onCommunitySubmitted = onCommunityEditSubmitted,
//        communityPost = communityPost,
//        navController= navController,
//    )
//}
//
//@Composable
//fun EditCommunityContent(
//    onCommunitySubmitted: (id:Long, placeName: String, location: String, description: String) -> Unit,
//    communityPost: ResponseCommunity,
//    viewModel: CommunityViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
//    navController: NavHostController,
//) {
//    val placeNameState = remember { dataCommunityState(initialText = communityPost.placeName.orEmpty()) }
//    val locationState = remember { dataCommunityState(initialText = communityPost.location.orEmpty()) }
//    val descriptionState = remember { dataCommunityState(initialText = communityPost.description.orEmpty()) }
//    var isEditCommunityClickedState by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    Box(
//        modifier = Modifier
//            .padding(15.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//        ) {
//
//            Text(
//                text = stringResource(id = R.string.add_community),
//                fontFamily = fonts,
//                fontWeight = FontWeight.ExtraBold,
//                color = purple100,
//                fontSize = 30.sp,
//            )
//            Text(
//                text = stringResource(id = R.string.share_connect),
//                fontFamily = fonts,
//                color = Color.Black,
//                fontSize = 15.sp,
//            )
//
//            Spacer(modifier = Modifier.height(15.dp))
//            CommunityTextField(textFieldStateCommunity = placeNameState , label ="Place Name")
//            Spacer(modifier = Modifier.height(8.dp))
//            CommunityTextField(textFieldStateCommunity = locationState , label ="Location")
//            Spacer(modifier = Modifier.height(8.dp))
//            CommunityTextFieldDescription(textFieldStateCommunity = descriptionState , label ="Description")
//
//            val onSubmit = {
//                isEditCommunityClickedState = true
//                if (placeNameState.isValid && locationState.isValid && descriptionState.isValid) {
//                    onCommunitySubmitted(
//                        communityPost.id ?: -1, // Pass the existing community post ID
//                        placeNameState.text,
//                        locationState.text,
//                        descriptionState.text
//                    )
//                }
//            }
//
//            Button(
//                onClick = {onSubmit()},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                enabled = placeNameState.isValid && locationState.isValid && descriptionState.isValid
//            ) {
//                when {
//                    isEditCommunityClickedState -> {
//                        when (val EditcommunityState = viewModel.defaultState.collectAsState().value) {
//                            is Result.Loading -> {
//                                CircularProgressIndicator(
//                                    modifier = Modifier
//                                        .size(30.dp)
//                                        .padding(4.dp),
//                                    color = white100
//                                )
//                            }
//
//                            is Result.Success<*> -> {
//                                val data = (EditcommunityState as? Result.Success<ResponseUser>)?.data
//                                data?.let {
//                                    Toast.makeText(context, "Successfully Added", Toast.LENGTH_LONG).show()
//                                    isEditCommunityClickedState = false
//                                    navController.navigate(Screen.Login.route) {
//                                        popUpTo(Screen.Community.route) {
//                                            inclusive = true
//                                        }
//                                    }
//                                }
//                            }
//
//                            is Result.Error -> {
//                                val errorState = EditcommunityState as? Result.Error
//                                val serverMsg = errorState?.message ?: "An unknown error occurred"
//                                Toast.makeText(context, serverMsg, Toast.LENGTH_LONG).show()
//                                isEditCommunityClickedState = false
//                            }
//
//                            else -> {}
//                        }
//                    }
//
//                    else -> {
//                        Text(
//                            color = white100,
//                            fontFamily = fonts,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 20.sp,
//                            text = stringResource(id = R.string.save)
//                        )
//                    }
//                }
//            }
//        }
//
//    }
//}
