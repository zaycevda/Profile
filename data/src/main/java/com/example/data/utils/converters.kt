package com.example.data.utils

import com.example.data.models.AuthBody
import com.example.data.models.AvatarBody
import com.example.data.models.AvatarsBody
import com.example.data.models.PhoneBody
import com.example.data.models.PhoneShortBody
import com.example.data.models.ProfileDataBody
import com.example.data.models.RefreshTokenBody
import com.example.data.models.SuccessBody
import com.example.data.models.TokensBody
import com.example.data.models.UserMiniBody
import com.example.data.models.UserShortBody
import com.example.domain.models.Auth
import com.example.domain.models.Avatar
import com.example.domain.models.Avatars
import com.example.domain.models.Phone
import com.example.domain.models.PhoneShort
import com.example.domain.models.RefreshToken
import com.example.domain.models.Success
import com.example.domain.models.Tokens
import com.example.domain.models.User
import com.example.domain.models.UserMini
import com.example.domain.models.UserShort

fun AuthBody.toAuth() =
    Auth(
        refreshToken = refreshToken,
        accessToken = accessToken,
        userId = userId,
        isUserExists = isUserExists
    )

fun Avatar.toAvatarBody() =
    AvatarBody(
        filename = filename,
        base64 = base64
    )

fun AvatarsBody.toAvatars() =
    Avatars(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar
    )

fun Phone.toPhoneBody() =
    PhoneBody(
        phone = phone,
        code = code
    )

fun PhoneShort.toPhoneShortBody() =
    PhoneShortBody(
        phone = phone
    )

fun RefreshToken.toRefreshTokenBody() =
    RefreshTokenBody(
        refreshToken = refreshToken
    )

fun SuccessBody.toSuccess() =
    Success(
        isSuccess = isSuccess
    )

fun TokensBody.toTokens() =
    Tokens(
        refreshToken = refreshToken,
        accessToken = accessToken,
        userId = userId
    )

fun ProfileDataBody.toUser() =
    User(
        name = name,
        username = username,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatar = avatar,
        id = id,
        last = last,
        online = online,
        created = created,
        phone = phone,
        completedTask = completedTask,
        avatars = avatarsBody.toAvatars()
    )

fun UserMini.toUserMiniBody() =
    UserMiniBody(
        phone = phone,
        name = name,
        username = username
    )

fun UserShort.toUserShortBody() =
    UserShortBody(
        name = name,
        username = username,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatarBody = avatar.toAvatarBody()
    )