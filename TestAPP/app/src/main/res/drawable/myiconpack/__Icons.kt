package myiconpack

import MyIconPack
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.List as ____KtList

public object IconsGroup

public val MyIconPack.Icons: IconsGroup
  get() = IconsGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconsGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf()
    return __AllIcons!!
  }
