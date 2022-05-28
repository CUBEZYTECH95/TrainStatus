package trainstatus.trainbooking.pnrstatus.livestatus
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import trainstatus.trainbooking.pnrstatus.R
import trainstatus.trainbooking.pnrstatus.adapter.OfflinelistAdapter
import trainstatus.trainbooking.pnrstatus.adapter.TestAdapter
import trainstatus.trainbooking.pnrstatus.apihelper.ApiInterface
import trainstatus.trainbooking.pnrstatus.clicklistner.OfflineSeatClick
import trainstatus.trainbooking.pnrstatus.clicklistner.TrainSeatClick
import trainstatus.trainbooking.pnrstatus.databinding.ActivityTrainBySearchBinding
import trainstatus.trainbooking.pnrstatus.model.NameOrCodeModelItem
import trainstatus.trainbooking.pnrstatus.model.PopularListModel
import trainstatus.trainbooking.pnrstatus.repository.MainRespository
import trainstatus.trainbooking.pnrstatus.utils.CommonUtil
import trainstatus.trainbooking.pnrstatus.utils.ModelFactory
import trainstatus.trainbooking.pnrstatus.viewmodel.MainViewModel
import java.io.IOException


class TrainBySearchActivity : BaseClass() {

    lateinit var binding: ActivityTrainBySearchBinding
    val apiInterface: ApiInterface =
        getclient(CommonUtil.FIND_TRAIN_NUMBER).create(ApiInterface::class.java)
    lateinit var mainViewModel: MainViewModel
    var adapter: TestAdapter? = null
    private val populartarinlist = ArrayList<PopularListModel>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_train_by_search)

        binding.rvPopular.visibility = View.VISIBLE

        binding.ivBack.setOnClickListener {

            onBackPressed()
        }

        mainViewModel = ViewModelProvider(
            this,
            ModelFactory(MainRespository(apiInterface))
        )[MainViewModel::class.java]

        mainViewModel.postData.observe(this, Observer {

            binding.rv.layoutManager = LinearLayoutManager(this@TrainBySearchActivity)
            adapter = TestAdapter(it.toMutableList(), trainSeatClick = object : TrainSeatClick {
                override fun click(photo: NameOrCodeModelItem) {

                    val intent =
                        Intent(this@TrainBySearchActivity, TopSeatCalenderActivity::class.java)
                    intent.putExtra("type", 2)
                    intent.putExtra("trainNo", photo.C)
                    intent.putExtra("sourceCode", photo.origin)
                    intent.putExtra("destinationCode", photo.destination)
                    intent.putExtra("trainName", photo.N)
                    startActivity(intent)
                }
            })
            binding.rv.adapter = adapter

        })

        binding.ivsearch.setOnClickListener {

            adapter?.clear()
            binding.rvPopular.visibility = View.VISIBLE
            binding.etSearchTrain.text.clear()

        }

        mainViewModel.showLoadingProg.observe(this, Observer {

            if (it) {

                binding.progressCircular.visibility = View.VISIBLE
                binding.ivsearch.visibility = View.GONE

            } else {

                binding.progressCircular.visibility = View.GONE
                binding.ivsearch.visibility = View.VISIBLE
            }
        })

        binding.etSearchTrain.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isNotEmpty()) {

                    binding.rv.visibility = View.VISIBLE
                    binding.rvPopular.visibility = View.GONE
                    binding.ivsearch.visibility = View.VISIBLE
                    mainViewModel.gettrainby(s.toString())

                } else {

                    binding.rv.visibility = View.GONE
                    binding.ivsearch.visibility = View.GONE
                    adapter?.clear()
                    adapter?.notifyDataSetChanged()
                    binding.rvPopular.visibility = View.VISIBLE

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        offlinelist()

    }


    fun offlinelist() {

        try {

            val obj = JSONArray(trainbyname())

            for (i in 0 until obj.length()) {

                val popularlist = PopularListModel(
                    obj.getJSONObject(i).getInt("c"),
                    obj.getJSONObject(i).getString("n"),
                    obj.getJSONObject(i).getString("origin"),
                    obj.getJSONObject(i).getString("originName"),
                    obj.getJSONObject(i).getString("destination"),
                    obj.getJSONObject(i).getString("destinationName")
                )

                populartarinlist.add(popularlist)

                binding.rvPopular.layoutManager = LinearLayoutManager(this@TrainBySearchActivity)
                binding.rvPopular.adapter =
                    OfflinelistAdapter(populartarinlist, offlineSeatClick = object :
                        OfflineSeatClick {
                        override fun click(photo: PopularListModel) {

                            val intent = Intent(
                                this@TrainBySearchActivity,
                                TopSeatCalenderActivity::class.java
                            )
                            intent.putExtra("type", 2)
                            intent.putExtra("trainNo", photo.number.toString())
                            intent.putExtra("sourceCode", photo.origin)
                            intent.putExtra("destinationCode", photo.destination)
                            intent.putExtra("trainName", photo.trainname)
                            startActivity(intent)
                        }
                    })

            }

        } catch (c: Exception) {

            c.printStackTrace()
        }


    }

    fun trainbyname(): String? {

        val json: String?

        try {
            val `is` = assets.open("trainlist.json")
            var size = 0
            size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return json

    }
}