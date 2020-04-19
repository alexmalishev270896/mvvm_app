package com.alex_malishev.data_layer.utils

import android.app.Application
import android.content.Context
import android.net.*

object NetworkStateHolder : NetworkState {

    private lateinit var holder: NetworkStateImp

    fun init(): NetworkStateImp{
        holder = NetworkStateImp()
        return holder
    }
    override val isConnected: Boolean
        get() = holder.isConnected
    override val network: Network?
        get() = holder.network
    override val networkCapabilities: NetworkCapabilities?
        get() = holder.networkCapabilities
    override val linkProperties: LinkProperties?
        get() = holder.linkProperties

}

fun Application.registerConnectivityMonitor() {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.registerNetworkCallback(
        NetworkRequest.Builder().build(), NetworkCallbackImp(NetworkStateHolder.init())
    )
}

interface NetworkState {
    val isConnected: Boolean
    val network: Network?
    val networkCapabilities: NetworkCapabilities?
    val linkProperties: LinkProperties?
}

class NetworkStateImp : NetworkState {
    override var isConnected: Boolean = false
    override var network: Network? = null
    override var linkProperties: LinkProperties? = null
    override var networkCapabilities: NetworkCapabilities? = null
}

internal class NetworkCallbackImp(private val holder: NetworkStateImp) :
    ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        holder.network = network
        holder.isConnected = true
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        holder.networkCapabilities = networkCapabilities
    }

    override fun onLost(network: Network) {
        holder.network = network
        holder.isConnected = false
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        holder.linkProperties = linkProperties
    }
}