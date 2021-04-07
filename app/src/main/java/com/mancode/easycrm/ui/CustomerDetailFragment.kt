package com.mancode.easycrm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mancode.easycrm.data.customers
import com.mancode.easycrm.ui.theme.EasyCrmTheme

class CustomerDetailFragment : Fragment() {
    
    private val args: CustomerDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EasyCrmTheme {
                    CustomerDetailsScreen(customers[args.customerId])
                }
            }
        }
    }

}