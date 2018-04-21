package com.findly.data.service.response

/**
 * Created by Wojdor on 20.04.2018.
 */
import com.google.gson.annotations.Expose

data class OffersResponse(
        @Expose val count: Number,
        @Expose val fallbackMode: FallbackMode,
        @Expose val offers: List<Offers>,
        @Expose val pageToken: PageToken,
        @Expose val searchScenarioName: String,
        @Expose val sponsoredOffers: Array<Any>
)

data class FallbackMode(@Expose val mode: String)

data class PageToken(@Expose val next: String, @Expose val previous: Any?)

data class Offers(
        @Expose val advert: Boolean,
        @Expose val auction: Boolean,
        @Expose val bidsCount: Number,
        @Expose val buyNow: Boolean,
        @Expose val cartAvailable: Boolean,
        @Expose val emphases: Emphases,
        @Expose val endingAt: String,
        @Expose val id: String,
        @Expose val images: Array<Images>,
        @Expose val infinite: Boolean,
        @Expose val location: Location,
        @Expose val loyalty: Loyalty,
        @Expose val name: String,
        @Expose val parameters: Array<Parameters>,
        @Expose val prices: Prices,
        @Expose val quantity: Quantity,
        @Expose val seller: Seller,
        @Expose val shipping: Shipping,
        @Expose val url: String,
        @Expose val vendor: String
)

data class Emphases(
        @Expose val bold: Boolean,
        @Expose val highlight: Boolean,
        @Expose val promoBadgeTopUrl: Any?,
        @Expose val promoBadgeUrl: Any?,
        @Expose val promoted: Boolean
)

data class Location(@Expose val countryCode: String, @Expose val location: String)

data class Loyalty(@Expose val coins: Number)

data class Prices(@Expose val current: LowestShippingCost, @Expose val withDelivery: LowestShippingCost)

data class Quantity(@Expose val unitType: String, @Expose val value: Number)

data class Seller(
        @Expose val allegroStandard: Boolean,
        @Expose val brandZone: Boolean,
        @Expose val id: Number,
        @Expose val superSeller: Boolean
)

data class Shipping(
        @Expose val free: Boolean,
        @Expose val freeReturn: Boolean,
        @Expose val lowestShippingCost: LowestShippingCost
)

data class Images(@Expose val url: String)

data class LowestShippingCost(@Expose val amount: String, @Expose val currency: String)

data class Parameters(
        @Expose val id: String,
        @Expose val name: String,
        @Expose val type: String,
        @Expose val values: Array<String>
)
