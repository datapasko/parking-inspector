package com.tapascodev.inspector.vehicles.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.snackbar
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.FragmentVehicleAddEditBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VehicleAddEditFragment : Fragment() {

    private lateinit var viewModel: VehicleViewModel
    private val args: VehicleAddEditFragmentArgs by navArgs()
    private var _binding: FragmentVehicleAddEditBinding? = null
    private val binding get() = _binding!!

    private var vehicleId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentVehicleAddEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[VehicleViewModel::class.java]
        initUI()
        observe()

        vehicleId = args.id

        if(vehicleId != null) {
            viewModel.getVehicle(vehicleId.toString())
        }
    }

    private fun myVehicle(): Vehicle {
        binding.apply {
            return Vehicle (
                id = args.id.toString(),
                plate = etPlateVehicle.text.toString(),
                brand = tvBrandVehicle.text.toString(),
                model = etModelVehicle.text.toString(),
                color = tvColorVehicle.text.toString(),
                name = etNameVehicle.text.toString(),
                email = etEmailVehicle.text.toString(),
                phone = etPhoneVehicle.text.toString(),
                address = etAddressVehicle.text.toString(),
                city = etCityVehicle.text.toString(),
                codePostal = etCodePostalVehicle.text.toString(),
            )
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vehicle.collect{
                    when(it) {
                        is Resource.Failure -> {
                            binding.progressAddEdit.visible(false)
                            requireView().snackbar(it.errorBody.toString())
                        }
                        is Resource.Loading -> {
                            binding.progressAddEdit.visible(true)
                        }
                        is Resource.Success -> {
                            binding.progressAddEdit.visible(false)
                            displayVehicle(it.value)
                        }
                        else -> {}
                    }
                }
            }
        }

        viewModel.formState.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Failure -> {
                    binding.progressAddEdit.visible(false)
                    requireView().snackbar(result.errorBody.toString())
                }
                Resource.Loading -> {
                    binding.progressAddEdit.visible(true)
                }
                is Resource.Success -> {
                    binding.progressAddEdit.visible(false)
                    requireView().snackbar("Vehicle add successfully")
                    findNavController().popBackStack()
                }
                else -> {}
            }

        }
    }

    private fun displayVehicle(vehicle: Vehicle) {
        (activity as HomeActivity).supportActionBar?.title = vehicle.plate
        binding.apply {
            etPlateVehicle.setText(vehicle.plate)
            tvBrandVehicle.setText(vehicle.brand, false)
            etModelVehicle.setText(vehicle.model)
            tvColorVehicle.setText(vehicle.color, false)
            etNameVehicle.setText(vehicle.name)
            etEmailVehicle.setText(vehicle.email)
            etPhoneVehicle.setText(vehicle.phone)
            etAddressVehicle.setText(vehicle.address)
            etCityVehicle.setText(vehicle.city)
            etCodePostalVehicle.setText(vehicle.codePostal)
        }
    }

    private fun clearVehicle() {
        binding.apply {
            etPlateVehicle.text = null
            tvBrandVehicle.setText(null, false)
            etModelVehicle.text = null
            tvColorVehicle.setText(null, false)
            etNameVehicle.text = null
            etEmailVehicle.text = null
            etPhoneVehicle.text = null
            etAddressVehicle.text = null
            etCityVehicle.text = null
            etCodePostalVehicle.text = null
        }
    }

    private fun initUI() {
        (activity as HomeActivity).supportActionBar?.title = "New vehicle"
        plateFocusListener()
        brandFocusListener()
        modelFocusListener()
        colorFocusListener()
        nameFocusListener()
        emailFocusListener()
        phoneFocusListener()
        addressFocusListener()
        cityFocusListener()
        codeFocusListener()
        setArrayOptions()

        binding.btnSaveVehicle.setOnClickListener {
            if(validationForm() && vehicleId == null)
                viewModel.saveVehicle(myVehicle())

            if(validationForm() && vehicleId != null)
                viewModel.updateVehicle(myVehicle())
        }
    }

    private fun validationForm(): Boolean {

        binding.apply {
            textLayoutPlate.helperText = validPlate()
            tLBrand.helperText = validBrand()
            tLModel.helperText = validModel()
            tLColor.helperText = validColor()
            tLName.helperText = validName()
            tLEmail.helperText = validEmail()
            tLPhone.helperText = validPhone()
            tLAddress.helperText = validAddress()
            tLCity.helperText = validCity()
            tLCodePostal.helperText = validCode()

            val validPlate = textLayoutPlate.helperText == null
            val validBrand= tLBrand.helperText == null
            val validModel= tLModel.helperText == null
            val validColor= tLColor.helperText == null
            val validName= tLName.helperText == null
            val validEmail= tLEmail.helperText == null
            val valPhone= tLPhone.helperText == null
            val validAddress= tLAddress.helperText == null
            val validCity= tLCity.helperText == null
            val validCode= tLCodePostal.helperText == null

            return validPlate && validBrand && validModel && validCode &&
                validColor && validName && validEmail &&
                valPhone && validAddress && validCity
        }
    }

    private fun plateFocusListener() {
        binding.etPlateVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.textLayoutPlate.helperText = validPlate() }
    }

    private fun validPlate(): String? {
        if(binding.etPlateVehicle.text.isNullOrEmpty())
            return getString(R.string.required)

        if(binding.etPlateVehicle.text.toString().length < 6)
            return "Minimum 6 Characters"

        return null
    }

    private fun brandFocusListener() {
        binding.tvBrandVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLBrand.helperText = validBrand() }
    }

    private fun validBrand(): String? {
        if(binding.tvBrandVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun modelFocusListener() {
        binding.etModelVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLModel.helperText = validModel() }
    }

    private fun validModel(): String? {
        if(binding.etModelVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun colorFocusListener() {
        binding.tvColorVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLColor.helperText = validColor() }
    }

    private fun validColor(): String? {
        if(binding.tvColorVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun nameFocusListener() {
        binding.etNameVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLName.helperText = validName() }
    }

    private fun validName(): String? {
        if(binding.etNameVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun emailFocusListener() {
        binding.etEmailVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLEmail.helperText = validEmail() }
    }

    private fun validEmail(): String? {
        val emailText = binding.etEmailVehicle.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
            return "Invalid Email Address"
        return null
    }

    private fun phoneFocusListener()
    {
        binding.etPhoneVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLPhone.helperText = validPhone() }
    }

    private fun validPhone(): String? {
        val phoneText = binding.etPhoneVehicle.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex()))
            return "Must be all Digits"

        if(phoneText.length != 9)
            return "Must be 9 Digits"

        return null
    }

    private fun addressFocusListener() {
        binding.etAddressVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLAddress.helperText = validAddress() }
    }

    private fun validAddress(): String? {
        if(binding.etAddressVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun cityFocusListener() {
        binding.etCityVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLCity.helperText = validCity() }
    }

    private fun validCity(): String? {
        if(binding.etCityVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun codeFocusListener() {
        binding.etCodePostalVehicle.setOnFocusChangeListener { _, focused -> if (!focused) binding.tLCodePostal.helperText = validCode() }
    }

    private fun validCode(): String? {
        if(binding.etCodePostalVehicle.text.isNullOrEmpty())
            return getString(R.string.required)
        return null
    }

    private fun setArrayOptions() {
        val priorities = resources.getStringArray(R.array.brand_items)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_select,priorities)
        binding.tvBrandVehicle.setAdapter(arrayAdapter)

        val arrayColor = resources.getStringArray(R.array.color_items)
        val arrayAdapterColor = ArrayAdapter(requireContext(), R.layout.item_select,arrayColor)
        binding.tvColorVehicle.setAdapter(arrayAdapterColor)
    }

    override fun onResume() {
        super.onResume()
        setArrayOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Limpiar el estado del formulario al salir, para que no se repita el éxito/error al regresar
        viewModel.resetFormState()
    }
}