package com.mancode.easycrm.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDialog : BottomSheetDialogFragment() {

    private val viewModel: CustomerDetailViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val args: NoteDialogArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.customerId = args.customerId
        viewModel.noteId = args.noteId
        return ComposeView(requireContext()).apply {
            setContent {
                NoteEditor(viewModel, navController)
            }
        }
    }
}