package com.sab.shoppinglist.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.observe
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.sab.shoppinglist.R
import com.sab.shoppinglist.viewmodels.ShoppingListViewModel
import com.sab.shoppinglist.adapters.ShoppingListAdapter
import com.sab.shoppinglist.databinding.FragmentShoppingListBinding
import com.sab.shoppinglist.databinding.LayoutNewShoppingItemBinding
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ShoppingListFragment : Fragment() {

    private lateinit var listBinding: FragmentShoppingListBinding
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private lateinit var newShoppingItemBinding: LayoutNewShoppingItemBinding
    private lateinit var viewModel: ShoppingListViewModel

    private var imageUri = "R.drawable.ic_shopping_cart_black_128dp"
    private fun List<ShoppingItem>.anyUnchecked() = any { !it.isChecked }

    companion object {
        const val GALLERY_INTENT_CODE = 777
        const val CAMERA_INTENT_CODE = 888
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listBinding = FragmentShoppingListBinding.inflate(inflater, container, false)
        listBinding.shoppingRecyclerList.apply {
            layoutManager = LinearLayoutManager(context)
            shoppingListAdapter = ShoppingListAdapter()
            adapter = shoppingListAdapter
            subscribeUi(shoppingListAdapter)
        }

        listBinding.addItemFloatingButton.setOnClickListener { showAddItemDialog() }
        listBinding.markAsBoughtButton.setOnClickListener {
            viewModel.allItems.value?.forEach {
                if (it.isChecked) {
                    it.isBought = true
                    it.boughtAgo = System.currentTimeMillis()
                }
                viewModel.updateItem(it)
            }
        }
        setHasOptionsMenu(true)
        return listBinding.root
    }

    private fun subscribeUi(adapter: ShoppingListAdapter) {
        viewModel.allItems.observe(viewLifecycleOwner) { items ->
            adapter.setShoppingList(items.filter { !it.isBought })
            if (items.none { !it.isBought })
                listBinding.emptyListText.visibility = View.VISIBLE
            else listBinding.emptyListText.visibility = View.INVISIBLE

            if (items.any { !it.isBought })
                listBinding.markAsBoughtButton.visibility = View.VISIBLE
            else listBinding.markAsBoughtButton.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top_shopping, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAction -> viewModel.allItems.value?.forEach {
                if (it.isChecked && !it.isBought) viewModel.deleteItem(it)
                shoppingListAdapter.notifyDataSetChanged()
            }

            R.id.checkAllAction ->
                when (viewModel.allItems.value?.anyUnchecked()) {
                    true -> viewModel.allItems.value?.forEach {
                        it.isChecked = true
                        shoppingListAdapter.notifyDataSetChanged()
                    }

                    false -> viewModel.allItems.value?.forEach {
                        it.isChecked = false
                        shoppingListAdapter.notifyDataSetChanged()
                    }
                }

            R.id.showHistoryAction -> Navigation.findNavController(this.requireView())
                .navigate(R.id.action_shoppingListFragment_to_shoppingHistoryFragment)
        }
        return true
    }

    private fun showAddItemDialog() {
        newShoppingItemBinding = LayoutNewShoppingItemBinding.inflate(layoutInflater)

        val addItemDialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .cornerRadius(30f)
            .customView(R.layout.layout_new_shopping_item, newShoppingItemBinding.root)

        newShoppingItemBinding.cancelButton.setOnClickListener {
            addItemDialog.dismiss()
        }
        newShoppingItemBinding.newItemImage.setOnClickListener {
            getImageFromCamera()
        }
        newShoppingItemBinding.browseGalleryButton.setOnClickListener {
            getImageFromGallery()
        }
        newShoppingItemBinding.addItemButton.setOnClickListener {
            CoroutineScope(IO).launch {
                var shoppingItem = ShoppingItem(
                    title = newShoppingItemBinding.newItemTitle.text.toString(),
                    amount = newShoppingItemBinding.newItemQuantityPicker.value,
                    imageUrl = imageUri
                )
                viewModel.addItem(shoppingItem)
                imageUri = "R.drawable.ic_shopping_cart_black_128dp"
            }
            addItemDialog.hide()
        }
        newShoppingItemBinding.newItemQuantityPicker.minValue = 1
        newShoppingItemBinding.newItemQuantityPicker.maxValue = 99

        addItemDialog.show()
    }

    private fun getImageFromCamera() {

        @Throws(IOException::class)
        fun createImageFile(): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File =
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                imageUri = absolutePath
            }
        }

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Toast.makeText(context, "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireActivity(), "com.sab.shoppinglist", it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_INTENT_CODE)
            }
        }
    }

    private fun getImageFromGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_INTENT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_INTENT_CODE && resultCode == RESULT_OK) {
            Glide.with(requireView()).load(imageUri)
                .into(newShoppingItemBinding.newItemImage)
        } else if (requestCode == GALLERY_INTENT_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data.toString()
            Glide.with(requireView()).load(imageUri).into(newShoppingItemBinding.newItemImage)
        }
    }
}