package com.example.orango.ui.cart

import androidx.fragment.app.Fragment

class CartFragment : Fragment() {

//    private lateinit var outputDirectory: File
//    private lateinit var cameraExecutor: ExecutorService
//
//    private lateinit var binding: FragmentCartBinding
//    private val viewModel: CartViewModel by viewModels()
//
//    private val cartAdapter by lazy {
//        CartAdapter(mutableMapOf())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentCartBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupAdapter()
//        outputDirectory = getOutputDirectory()
//        cameraExecutor = Executors.newSingleThreadExecutor()
//
//        binding.include55.checkout.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                try {
//                    Api.retrofitService.addReceipt(
//                        viewModel.savedCustomerData.user.id,
//                        cartAdapter.getMapOfReceycle()
//                    )
//                }catch (ex:Exception){
//                    ex.printStackTrace()
//                }
//            }
//        }
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.CAMERA
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.CAMERA),
//                CAMERA_PERMISSION_REQUEST_CODE
//            )
//        } else {
//            lifecycleScope.launch {
//                startCamera()
//            }
//        }
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
//
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//
//            val preview = Preview.Builder()
//                .build()
//                .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }
//
//            val imageCapture = ImageCapture.Builder()
//                .setTargetRotation(Surface.ROTATION_0)
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .build()
//
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                cameraProvider.unbindAll()
//
//                cameraProvider.bindToLifecycle(
//                    viewLifecycleOwner,
//                    cameraSelector,
//                    preview,
//                    imageCapture
//                )
//
//                captureImageEveryThreeSeconds(imageCapture)
//            } catch (e: Exception) {
//                // Handle camera setup error
//            }
//        }, ContextCompat.getMainExecutor(requireContext()))
//    }
//
//    private fun captureImageEveryThreeSeconds(imageCapture: ImageCapture) {
//        lifecycleScope.launch {
//            while (true) {
//                Api.retrofitArdour.setColor("FFFFFFFF")
//                val imageFile = createImageFile(outputDirectory)
//                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()
//
//                withContext(Dispatchers.IO) {
//                    imageCapture.takePicture(
//                        outputFileOptions,
//                        cameraExecutor,
//                        object : ImageCapture.OnImageSavedCallback {
//                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                                lifecycleScope.launch {
//                                    try {
//                                        val map = viewModel.detectProduct(imageFile)
//                                        Api.retrofitArdour.setColor("00FF00FF")
//                                        cartAdapter.updateList(map.toMap())
//                                        binding.include.itemsNo.text = "Items: " + cartAdapter.itemCount
//                                        binding.include.totalPrice.text = "Total Price: " + cartAdapter.getTotalPrice() + " L.E"
//                                        delay(1000)
//                                    } catch (ex: Exception) {
//                                        Toast.makeText(
//                                            requireContext(),
//                                            "Bad internet Connection",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        ex.printStackTrace()
//                                    }
//                                }
//                            }
//
//                            override fun onError(exception: ImageCaptureException) {
//                                // Handle error
//                            }
//                        })
//                    delay(1000)
//                }
//            }
//        }
//    }
//
//
//    private fun createImageFile(outputDirectory: File): File {
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//        val imageFileName = "IMG_$timeStamp.jpg"
//        return File(outputDirectory, imageFileName)
//    }
//
//    private fun getOutputDirectory(): File {
//        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
//        }
//        return if (mediaDir != null && mediaDir.exists())
//            mediaDir else requireActivity().filesDir
//    }
//
//    private fun setupAdapter() {
//        binding.productRecycleView.adapter = cartAdapter
//    }
//
//    override fun onDestroyView() {
//        lifecycleScope.launch {
//            Api.retrofitArdour.setColor("FFFFFFFF")
//        }
//        super.onDestroyView()
//        cameraExecutor.shutdown()
//    }
//
//    companion object {
//        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
//    }
}