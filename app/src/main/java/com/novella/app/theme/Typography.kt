package com.novella.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.novella.app.R

// Fonts via downloadable font resources in res/font
private val NotoSansArabicBold = FontFamily(Font(R.font.noto_sans_arabic_bold))
private val NotoNaskhArabicRegular = FontFamily(Font(R.font.noto_naskh_arabic_regular))
private val RobotoBold = FontFamily(Font(R.font.roboto_bold))
private val RobotoRegular = FontFamily(Font(R.font.roboto_regular))

data class NovellaTypographySet(
    val headings: Typography,
    val body: Typography
)

val TypographyEN = Typography(
    displayLarge = TextStyle(fontFamily = RobotoBold, fontWeight = FontWeight.Bold, fontSize = 32.sp),
    titleLarge = TextStyle(fontFamily = RobotoBold, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    bodyLarge = TextStyle(fontFamily = RobotoRegular, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = RobotoRegular, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = RobotoBold, fontWeight = FontWeight.Medium, fontSize = 14.sp)
)

val TypographyAR = Typography(
    displayLarge = TextStyle(fontFamily = NotoSansArabicBold, fontWeight = FontWeight.Bold, fontSize = 32.sp),
    titleLarge = TextStyle(fontFamily = NotoSansArabicBold, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    bodyLarge = TextStyle(fontFamily = NotoNaskhArabicRegular, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = NotoNaskhArabicRegular, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = NotoSansArabicBold, fontWeight = FontWeight.Medium, fontSize = 14.sp)
)
