package com.ludovic.vimont.bookfinder.model

data class GoogleBooksResponse(
	val totalItems: Int? = null,
	val kind: String? = null,
	val items: List<ItemsItem?>? = null
)

data class VolumeInfo(
	val industryIdentifiers: List<IndustryIdentifiersItem?>? = null,
	val pageCount: Int? = null,
	val printType: String? = null,
	val readingModes: ReadingModes? = null,
	val previewLink: String? = null,
	val canonicalVolumeLink: String? = null,
	val description: String? = null,
	val language: String? = null,
	val title: String? = null,
	val panelizationSummary: PanelizationSummary? = null,
	val publishedDate: String? = null,
	val maturityRating: String? = null,
	val allowAnonLogging: Boolean? = null,
	val contentVersion: String? = null,
	val authors: List<String?>? = null,
	val infoLink: String? = null
)

data class IndustryIdentifiersItem(
	val identifier: String? = null,
	val type: String? = null
)

data class ReadingModes(
	val image: Boolean? = null,
	val text: Boolean? = null
)

data class Pdf(
	val isAvailable: Boolean? = null
)

data class PanelizationSummary(
	val containsImageBubbles: Boolean? = null,
	val containsEpubBubbles: Boolean? = null
)

data class Epub(
	val isAvailable: Boolean? = null
)

data class ItemsItem(
	val saleInfo: SaleInfo? = null,
	val kind: String? = null,
	val volumeInfo: VolumeInfo? = null,
	val etag: String? = null,
	val id: String? = null,
	val accessInfo: AccessInfo? = null,
	val selfLink: String? = null
)

data class SaleInfo(
	val country: String? = null,
	val isEbook: Boolean? = null,
	val saleability: String? = null
)

data class AccessInfo(
	val accessViewStatus: String? = null,
	val country: String? = null,
	val viewability: String? = null,
	val pdf: Pdf? = null,
	val webReaderLink: String? = null,
	val epub: Epub? = null,
	val publicDomain: Boolean? = null,
	val quoteSharingAllowed: Boolean? = null,
	val embeddable: Boolean? = null,
	val textToSpeechPermission: String? = null
)