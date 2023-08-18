package com.ec25p5e.notesapp.feature_cam.domain.use_case

data class CameraUseCases(
    val getCameraForRegions: GetCameraForRegions,
    val getCameraDetail: GetCameraDetail,
    val getCameraForGlobalMap: GetCameraForGlobalMap,

    val getContinents: GetContinents
)