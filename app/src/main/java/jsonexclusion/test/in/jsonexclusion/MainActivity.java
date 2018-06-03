package jsonexclusion.test.in.jsonexclusion;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import jsonexclusion.test.in.jsonexclusion.ViewModel.MainVM;
import jsonexclusion.test.in.jsonexclusion.databinding.ActivityMainBinding;
import jsonexclusion.test.in.jsonexclusion.model.Variants;
import jsonexclusion.test.in.jsonexclusion.ui.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainAdapter mainAdapter;
    private MainVM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpObservers();
        setupAdapter();
        getData();
    }

    private void getData() {
        viewModel.fetchApi(this);
    }

    private void setUpObservers() {
        viewModel = ViewModelProviders.of(this).get(MainVM.class);
        viewModel.getError().observe(this, this::showError);
        viewModel.getVariantsLD().observe(this, this::updateList);
        viewModel.getProgress().observe(this, binding::setProgressVisibility);
        viewModel.getChoiceObserver().observe(this, ignore -> notifyChoices());
    }

    private void setupAdapter() {
        mainAdapter = new MainAdapter(viewModel.selectionObserver());
        binding.variants.setAdapter(mainAdapter);
    }

    private void updateList(Variants variants) {
        mainAdapter.setVariants(variants.variantGroups(), viewModel.getExclusions());
    }

    private void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    private void notifyChoices() {
        mainAdapter.setChoices(viewModel.getSelections());
    }
}
