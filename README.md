# GloKtor

GloKtor | gl·ok·tor | is a Kotlin Glo Boards SDK featuring a DSL for
building GloAPI GET queries. 

POST queries are coming soon!

## USAGE

First we initialize the main interface using
either a [Personal Authentication Token](https://support.gitkraken.com/developers/pats/) or the principle token
returned from an [OAuth 2.0 flow.](https://support.gitkraken.com/developers/oauth/)

``` Kotlin
val glo: GloApi = GloApi(
    personalAuthenticationToken = "some-token-goes-here"
    )
```

Next step is to make a call with the desired parameters:

``` Kotlin
val user: GloUser = glo.queryUser() {
    addName()
    addUserName()
    addEmail()
    addCreatedDate()
}
```

Thanks to the built in anti-corruption layer the returned data classes will
never contain `null` values, just default implementations.