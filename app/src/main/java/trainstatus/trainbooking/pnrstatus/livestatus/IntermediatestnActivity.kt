package trainstatus.trainbooking.pnrstatus.livestatus

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import trainstatus.trainbooking.pnrstatus.R
import trainstatus.trainbooking.pnrstatus.adapter.TrainScheduleAdapter
import trainstatus.trainbooking.pnrstatus.apihelper.ApiInterface
import trainstatus.trainbooking.pnrstatus.application.TrainPays
import trainstatus.trainbooking.pnrstatus.databinding.ActivityIntermediatestnBinding
import trainstatus.trainbooking.pnrstatus.repository.MainRespository
import trainstatus.trainbooking.pnrstatus.utils.ModelFactory
import trainstatus.trainbooking.pnrstatus.viewmodel.MainViewModel

class IntermediatestnActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntermediatestnBinding
    lateinit var mainViewModel: MainViewModel
    var trainNo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intermediatestn)


        binding.rvToolbar.setOnClickListener {

            onBackPressed()
        }

        trainNo = intent.getStringExtra("trainNo")

        val apiInterface: ApiInterface = TrainPays.getClient().create(ApiInterface::class.java)

        mainViewModel =
            ViewModelProvider(
                this,
                ModelFactory(MainRespository(apiInterface))
            )[MainViewModel::class.java]

        mainViewModel.schedulewithintermediatestn(trainNo)

        mainViewModel.trainlist.observe(this) {

            /* if (jsonObject1["first"].isJsonNull) null else jsonObject1["first"].asString*/

            binding.apply {


                rv.layoutManager = LinearLayoutManager(this@IntermediatestnActivity)
                rv.adapter = TrainScheduleAdapter(this@IntermediatestnActivity, it, trainNo)

            }


        }

        mainViewModel.errorMessage.observe(this, Observer {

            Toast.makeText(this@IntermediatestnActivity, it, Toast.LENGTH_SHORT).show()
        })

        mainViewModel.showLoadingProg.observe(
            this
        ) {

            if (it) {

                binding.progressCircular.visibility = View.VISIBLE
            } else {

                binding.progressCircular.visibility = View.GONE
            }
        }


    }
}