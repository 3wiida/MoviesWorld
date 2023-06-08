package com.ewida.rickmorti.ui.home.fragments.profile

import androidx.fragment.app.viewModels
import com.ewida.rickmorti.R
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.custom_view.dialogs.ContactUsBottomSheet
import com.ewida.rickmorti.custom_view.dialogs.LogoutDialog
import com.ewida.rickmorti.custom_view.dialogs.ProfileBottomSheet
import com.ewida.rickmorti.databinding.ProfileLayoutBinding
import com.ewida.rickmorti.ui.home.fragments.profile.adapters.ContactMethodsAdapter
import com.ewida.rickmorti.ui.home.fragments.profile.adapters.ProfileItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileLayoutBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    private var profileItemAdapter: ProfileItemAdapter? = ProfileItemAdapter()
    private var contactMethodsAdapter: ContactMethodsAdapter? = ContactMethodsAdapter()
    private var profileBottomSheet: ProfileBottomSheet? = null
    private var contactUsBottomSheet: ContactUsBottomSheet? = null
    private var logoutDialog: LogoutDialog? = null
    override fun getViewBinding() = ProfileLayoutBinding.inflate(layoutInflater)

    override fun initClicks() {
        profileItemAdapter?.onItemClick = { item ->
            when (item.id) {
                1 -> {
                    profileBottomSheet =
                        ProfileBottomSheet(
                            title = getString(R.string.termsandconditions),
                            content = getString(R.string.termsContent)
                        )
                    profileBottomSheet!!.show(requireActivity().supportFragmentManager, null)
                }

                2 -> {
                    profileBottomSheet =
                        ProfileBottomSheet(
                            title = getString(R.string.aboutus),
                            content = getString(R.string.aboutUsContent)
                        )
                    profileBottomSheet!!.show(requireActivity().supportFragmentManager, null)
                }

                3 -> {
                    contactUsBottomSheet = ContactUsBottomSheet(
                        adapter = contactMethodsAdapter!!,
                        list = viewModel.getContactMethodsList()
                    )
                    contactUsBottomSheet?.show(requireActivity().supportFragmentManager, null)
                }

                4 -> {
                    logoutDialog = LogoutDialog(requireContext(),requireActivity())
                    logoutDialog?.show()
                }
            }
        }
    }

    override fun setUpViews() {
        profileItemAdapter?.submitList(viewModel.getServiceList(requireContext()))
        binding.servicesRv.adapter = profileItemAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileItemAdapter = null
        contactMethodsAdapter = null
        contactUsBottomSheet = null
        profileBottomSheet = null
        logoutDialog = null
    }
}