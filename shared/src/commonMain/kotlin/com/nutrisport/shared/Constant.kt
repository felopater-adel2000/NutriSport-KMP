package com.nutrisport.shared

object Constant {

    const val WEB_CLIENT_ID = "74042734381-1jlsk03am3jce3941l3sc9jcc8bgebct.apps.googleusercontent.com"

    const val PAYPAL_CLIENT_ID = ""
    const val PAYPAL_SECRET_ID = ""

    const val PAYPAL_AUTH_KEY = "$PAYPAL_CLIENT_ID:$PAYPAL_SECRET_ID"

    const val PAYPAL_AUTH_ENDPOINT = "https://api-m.sandbox.paypal.com/v1/oauth2/token"
    const val PAYPAL_CHECKOUT_ENDPOINT = "https://api-m.sandbox.paypal.com/v2/checkout/orders"

    const val RETURN_URL = "com.stevdza.san.nutrisport://paypalpay?success=true"
    const val CANCEL_URL = "com.stevdza.san.nutrisport://paypalpay?cancel=true"

    const val MAX_QUANTITY = 10
    const val MIN_QUANTITY = 1

}