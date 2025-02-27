package edu.cnm.deepdive.notes.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.notes.databinding.FragmentLoginBinding;
import edu.cnm.deepdive.notes.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

  private FragmentLoginBinding binding;
  private LoginViewModel viewModel;
  private ActivityResultLauncher<Intent> launcher;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentLoginBinding.inflate(inflater, container, false);
    binding.signIn.setOnClickListener((v) -> viewModel.startSignIn(launcher));
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel = new ViewModelProvider(requireActivity())
        .get(LoginViewModel.class);
    viewModel
        .getAccount()
        .observe(getViewLifecycleOwner(), account -> {
          // TODO: 2/27/2025 navigate to home fragment
        });
    viewModel
        .getSignInThrowable()
        .observe(getViewLifecycleOwner(), throwable -> {
          if (throwable != null) {
            // TODO: 2/27/2025 show snackbar with signin failure
          }
        });

    launcher = registerForActivityResult(new StartActivityForResult(),
        (result) -> viewModel.completeSignIn(result));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}
