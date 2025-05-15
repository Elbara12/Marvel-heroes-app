package com.example.finalapplication.data.model

data class Hero(
    val id: Int = 0,
    val name: String = "",
    val slug: String = "",
    val powerstats: Powerstats = Powerstats(),
    val appearance: Appearance = Appearance(),
    val biography: Biography = Biography(),
    val work: Work = Work(),
    val connections: Connections = Connections(),
    val images: Images = Images()
)

data class Powerstats(
    val intelligence: Int = 0,
    val strength: Int = 0,
    val speed: Int = 0,
    val durability: Int = 0,
    val power: Int = 0,
    val combat: Int = 0
)

data class Appearance(
    val gender: String = "",
    val race: String? = null,
    val height: List<String> = emptyList(),
    val weight: List<String> = emptyList(),
    val eyeColor: String = "",
    val hairColor: String = ""
)

data class Biography(
    val fullName: String = "",
    val alterEgos: String = "",
    val aliases: List<String> = emptyList(),
    val placeOfBirth: String = "",
    val firstAppearance: String = "",
    val publisher: String = "",
    val alignment: String = ""
)

data class Work(
    val occupation: String = "",
    val base: String = ""
)

data class Connections(
    val groupAffiliation: String = "",
    val relatives: String = ""
)

data class Images(
    val xs: String = "",
    val sm: String = "",
    val md: String = "",
    val lg: String = ""
)

