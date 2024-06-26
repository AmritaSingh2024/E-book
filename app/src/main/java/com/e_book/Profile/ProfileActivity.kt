package com.e_book.Profile
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.e_book.R
import com.e_book.RoomDatabase.AppDatabase
import com.e_book.model.GetProfileResponse
import com.e_book.model.UpdateUserProfileRequest
import com.e_book.model.UpdateUserProfileResponse
import com.e_book.viewmodel.UserProfileViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: CircleImageView
    private lateinit var nameEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var otherRadioButton: RadioButton
    private lateinit var mobileNoEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var editButton: Button
    private lateinit var saveButton: Button

    private val userProfileViewModel: UserProfileViewModel by viewModels()

    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize UI elements
        profileImage = findViewById(R.id.profileImage)
        nameEditText = findViewById(R.id.name)
        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        emailEditText = findViewById(R.id.email)
        dobEditText = findViewById(R.id.dob)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)
        maleRadioButton = findViewById(R.id.maleRadioButton)
        femaleRadioButton = findViewById(R.id.femaleRadioButton)
        otherRadioButton = findViewById(R.id.otherRadioButton)
        mobileNoEditText = findViewById(R.id.mobileNo)
        addressEditText = findViewById(R.id.address)
        editButton = findViewById(R.id.EditBtn)
        saveButton = findViewById(R.id.SaveBtn)

        // Disable editable fields initially
        disableFields()

        // Set DOB EditText to show date picker dialog on click
        dobEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Observe profile data
        userProfileViewModel.profileLiveData.observe(this, { profile ->
            profile?.let {
                populateUI(it)
            }
        })

        // Observe errors
        userProfileViewModel.errorLiveData.observe(this, { error ->
            error?.let {
                showError(it)
            }
        })

        // Observe update success
        userProfileViewModel.updateSuccessLiveData.observe(this, { updatedProfile ->
            updatedProfile?.let {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                disableFields() // Disable fields after successful update
                toggleEditMode(false) // Toggle back to view mode after saving

                // Log the response
                logUpdateResponse(it)
            }
        })

        // Fetch user profile data
        fetchUserProfile()

        // Handle edit button click
        editButton.setOnClickListener {
            toggleEditMode(true) // Enable editable fields
        }

        // Handle save button click
        saveButton.setOnClickListener {
            lifecycleScope.launch {
                saveUpdatedProfile()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the DOB EditText with the selected date in yyyy-MM-dd format
                val formattedDate = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                dobEditText.setText(formattedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun fetchUserProfile() {
        lifecycleScope.launch {
            val userId = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(this@ProfileActivity).appDao().getUserId()
            }
            userProfileViewModel.getUserProfile(userId)
        }
    }

    private fun populateUI(profile: GetProfileResponse) {
        nameEditText.setText(profile.userName)
        firstNameEditText.setText(profile.firstName ?: "")
        lastNameEditText.setText(profile.lastName ?: "")
        emailEditText.setText(profile.email ?: "")

        // Format date of birth
        val dobFormatted = profile.dob?.substringBefore("T") ?: ""
        dobEditText.setText(dobFormatted)

        // Set mobile number and address
        mobileNoEditText.setText(profile.mobileNo ?: "")
        addressEditText.setText(profile.address ?: "")

        when (profile.gender) {
            0 -> maleRadioButton.isChecked = true
            1 -> femaleRadioButton.isChecked = true
            else -> otherRadioButton.isChecked = true
        }
    }

    private suspend fun saveUpdatedProfile() {
        val updatedProfile = gatherUpdatedProfileData()
        userProfileViewModel.updateUserProfile(updatedProfile)
    }

    private suspend fun gatherUpdatedProfileData(): UpdateUserProfileRequest {
        val userId = withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(this@ProfileActivity).appDao().getUserId()
        }
        return UpdateUserProfileRequest(
            userId = userId,
            userName = nameEditText.text.toString(),
            firstName = firstNameEditText.text.toString(),
            lastName = lastNameEditText.text.toString(),
            email = emailEditText.text.toString(),
            gender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.maleRadioButton -> 0
                R.id.femaleRadioButton -> 1
                else -> -1 // Handle other cases accordingly
            },
            deviceId = "",
            dob = dobEditText.text.toString(), // Date of birth
            mobileNo = mobileNoEditText.text.toString(),
            address = addressEditText.text.toString()
        )
    }

    private fun logUpdateResponse(response: UpdateUserProfileResponse) {
        Log.d("ProfileActivity", "Update Response: $response")
        Toast.makeText(this, "User: ${response.userName}", Toast.LENGTH_LONG).show()
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun disableFields() {
        // Disable all editable fields
        nameEditText.isEnabled = false
        firstNameEditText.isEnabled = false
        lastNameEditText.isEnabled = false
        emailEditText.isEnabled = false
        dobEditText.isEnabled = false
        maleRadioButton.isEnabled = false
        femaleRadioButton.isEnabled = false
        otherRadioButton.isEnabled = false
        mobileNoEditText.isEnabled = false
        addressEditText.isEnabled = false
        saveButton.visibility = Button.GONE // Hide save button in view mode
    }

    private fun enableFields() {
        // Enable all editable fields
        nameEditText.isEnabled = true
        firstNameEditText.isEnabled = true
        lastNameEditText.isEnabled = true
        emailEditText.isEnabled = true
        dobEditText.isEnabled = true
        maleRadioButton.isEnabled = true
        femaleRadioButton.isEnabled = true
        otherRadioButton.isEnabled = true
        mobileNoEditText.isEnabled = true
        addressEditText.isEnabled = true
        saveButton.visibility = Button.VISIBLE // Show save button in edit mode
    }

    private fun toggleEditMode(editMode: Boolean) {
        isEditMode = editMode
        if (editMode) {
            enableFields() // Enable fields for editing
            dobEditText.setOnClickListener {
                showDatePickerDialog()
            }
            editButton.visibility = Button.GONE // Hide edit button in edit mode
            saveButton.visibility = Button.VISIBLE // Show save button in edit mode
        } else {
            disableFields() // Disable fields after saving
            editButton.visibility = Button.VISIBLE // Show edit button in view mode
            saveButton.visibility = Button.GONE // Hide save button in view mode
        }
    }
}
