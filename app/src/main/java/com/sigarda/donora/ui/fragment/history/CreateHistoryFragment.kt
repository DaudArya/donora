package com.sigarda.donora.ui.fragment.history

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.history.Create.CreateHistoryRequestBody
import com.sigarda.donora.databinding.FragmentCraeteHistoryBinding
import com.sigarda.donora.ui.fragment.auth.login.LoginViewModel
import com.sigarda.donora.ui.fragment.base.BaseFragment
import com.sigarda.donora.utils.Constant
import com.sigarda.donora.utils.Extension.loadImage
import com.sigarda.donora.utils.Extension.showLongToast
import com.sigarda.donora.utils.Extension.toFormat
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@AndroidEntryPoint
class CreateHistoryFragment : BaseFragment() {

    private var _binding: FragmentCraeteHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()
    private val authViewModel : LoginViewModel by viewModels()

    private var selectedDate: Date? = null
    private var image_uri: Uri? = null
    private var imageFile: File? = null
    private var receipt_donor: MultipartBody.Part? = null


    private val CAMERA_PERMISSION_CODE = 1
    private val GALLERY_PERMISSION_CODE = 2
    private val IMAGE_PICK_CODE = 3


    private val permissions = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                requireContext().loadImage(result, binding.historyImage)
            }
        }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                requireContext().loadImage(bitmap, binding.historyImage)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCraeteHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewVisibility = View.GONE

        calendarItem()
        chooseImage()

        binding.btSubmitHistory.setOnClickListener(){
            postHistory()
        }
    }

    private fun postHistory(){

        if (validateInput()) {
            val description = binding.etDesc.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val date = binding.etDate.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            authViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                viewModel.postHistoryUser(description,date,receipt_donor!!,"Bearer $it")
            }
        }

    }

    private fun calendarItem() {
        binding.etDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_date_button))
                    .setSelection(selectedDate?.time)
                    .build()
            datePicker.addOnPositiveButtonClickListener { timestamp ->
                val selectedUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                selectedUtc.timeInMillis = timestamp
                val selectedLocal = Calendar.getInstance()
                selectedLocal.clear()
                selectedLocal.set(
                    selectedUtc.get(Calendar.YEAR),
                    selectedUtc.get(Calendar.MONTH),
                    selectedUtc.get(Calendar.DATE)
                )
                selectedDate = selectedLocal.time
                binding.etDate.text = selectedLocal.time.toFormat(Constant.CURRENT_DATE_FORMAT)
            }
            datePicker.show(parentFragmentManager, Constant.TAG_DATE_PICKER)
        }
    }


    private fun chooseImage() {
        binding.historyImage.setOnClickListener {
            cameraCheckPermission()
        }
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setMessage("Required permission for this feature")
            .setPositiveButton("Go to settings") { _, _ ->
                try {
                    val intent = Intent()
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)

                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = uri

                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
    }

    private fun cameraCheckPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(reports: MultiplePermissionsReport?) {
                    reports?.let {
                        if (reports.areAllPermissionsGranted()) {
                            chooseImageDialog()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                    showRotationalDialogForPermission()
                }
            })
            .onSameThread()
            .withErrorListener {
                requireContext().showLongToast(it.name)
            }
            .check()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setMessage("Choose an Image")
            .setPositiveButton("Gallery") { _, _ -> openGalleryHistory() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun gallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun camera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            cameraResult.launch(it)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_PICK_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            val imageFile = createImageFile()
            val imageUri = FileProvider.getUriForFile(
                requireContext(),
                "com.sigarda.donora.fileprovider",
                imageFile
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, IMAGE_PICK_CODE)
        } else {
            Toast.makeText(requireContext(), "Camera is not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            val imageUri = data?.data
            val imageFile = getImageFile(imageUri)

//            val imageFile = getCurrentImageFile()

            val imageBitmap = decodeUriToBitmap(imageUri)
            binding.historyImage.setImageBitmap(imageBitmap)

            if (imageFile != null) {

            }

            Log.d("image", imageUri.toString())
        }
    }

    private fun decodeUriToBitmap(uri: Uri?): Bitmap? {
        try {
            val inputStream = uri?.let { requireContext().contentResolver.openInputStream(it) }
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getImageFile(imageUri: Uri?): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = imageUri?.let {
            requireContext().contentResolver.query(it, filePathColumn, null, null, null)
        }
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val imagePath = columnIndex?.let { cursor?.getString(it) }
        cursor?.close()

        return imagePath?.let { File(it) }
    }
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        return imageFile
    }
    private fun getCurrentImageFile(): File {
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFileName = "JPEG_" + getCurrentTimeStamp() + ".jpg"
        return File(storageDir, imageFileName)
    }

    private fun getCurrentTimeStamp(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return timeStamp
    }

    fun openGalleryHistory() {
        binding.historyImage.setOnClickListener {
            changePicture.launch("image/*")
        }
    }


    private val changePicture =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = requireContext().contentResolver
                val type = contentResolver.getType(it)
                image_uri = it

                val fileNameimg = "${System.currentTimeMillis()}"
                binding.historyImage.setImageURI(it)
                Toast.makeText(requireContext(), "$image_uri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("dnr-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                receipt_donor =
                    MultipartBody.Part.createFormData("receipt_donor", tempFile.name, requestBody)
            }
        }



    private fun validateInput(): Boolean {
        var isValid = true
        val description = binding.etDesc.text.toString().trim()
        val date = binding.etDate.text.toString().trim()

        if (description.isEmpty()) {
            isValid = false
            binding.etDesc.error = "Masih Kosong"
        }
        if (date.isEmpty()) {
            isValid = false
            binding.etDate.error = "Masih Kosong"
        }

        return isValid
    }
}