package com.example.travelapplication;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travelapplication.adapters.AirplaneSeatsAdapter;
import com.example.travelapplication.adapters.TravellerTabsAdapter;
import com.example.travelapplication.databinding.ActivitySelectSeatBinding;
import com.example.travelapplication.utils.AirplaneSeat;
import com.example.travelapplication.utils.FlightTicketUtils;
import com.example.travelapplication.utils.TravellerTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectSeatActivity extends AppCompatActivity {

    ActivitySelectSeatBinding binding;
    String[] travellerTabStringArray;
    TravellerTabsAdapter travellerTabsAdapter;
    List<TravellerTab> travellerTabList;
    List<AirplaneSeat> seatListA;
    AirplaneSeatsAdapter seatsAdapterA;
    List<AirplaneSeat> seatListB;
    AirplaneSeatsAdapter seatsAdapterB;
    List<AirplaneSeat> seatListC;
    AirplaneSeatsAdapter seatsAdapterC;
    List<AirplaneSeat> seatListD;
    AirplaneSeatsAdapter seatsAdapterD;

    AirplaneSeat selectedAirplaneSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSeatBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get intent and data from previous activity
        Intent intent1 = getIntent();
        // Get selected tickett
        FlightTicketUtils.FlightTicket selectedTicket =
                (FlightTicketUtils.FlightTicket)
                        intent1.getSerializableExtra(FlightTicketUtils.SELECTED_FLIGHT);

        // Init Total Price text
        String seatPriceText = null;
        if (selectedTicket != null) {
            seatPriceText = String.format(Locale.ENGLISH, "$%.2f",
                    (double) (selectedTicket.price * FlightTicketUtils.adultsNum));
        }
        // Init price of the seat
        binding.seatPriceValue.setText(seatPriceText);

        // Init Traveller Tabs, TravellerTabArray and Selected seat
        initTravellerTabsReyclerView();

        // Init Seats
        initSeatRecyclerViewA();
        initSeatRecyclerViewB();
        initSeatRecyclerViewC();
        initSeatRecyclerViewD();

        // Set selected seat
        selectedAirplaneSeat = seatsAdapterA.getItem(1);
        setTravellerAndSeatText();
        String seatCode = String.valueOf(selectedAirplaneSeat.getSeatRow())
                + selectedAirplaneSeat.getSeatColumn();
        travellerTabStringArray[0] = seatCode;

        // Init back button
        initBackButton();

        // Init continue button
        initContinueButton(selectedTicket);
    }

    private void initContinueButton(FlightTicketUtils.FlightTicket selectedTicket) {
        // Set on click listener
        binding.seatContinueButton.setOnClickListener(v -> {
            // Update for current traveller in travellerTabStringArray
            int currentTravellerIndex = travellerTabsAdapter.getSelectedIndex();
            String newSeatCode = String.valueOf(selectedAirplaneSeat.getSeatRow())
                    + selectedAirplaneSeat.getSeatColumn();
            travellerTabStringArray[currentTravellerIndex] = newSeatCode;
            // Check if the user has select seats for all travellers
            boolean finishSelect = isSelectSeatsForAllTravellers();
            if(!finishSelect) {
                Toast.makeText(this,
                        "Please select seat all travellers", Toast.LENGTH_SHORT).show();
            } else {
                // Move to Boarding pass
                Intent intent = new Intent(this, BoardingPassActivity.class);
                intent.putExtra(FlightTicketUtils.SELECTED_FLIGHT, selectedTicket);
                intent.putExtra(FlightTicketUtils.SEAT_LIST, travellerTabStringArray);
                startActivity(intent);
            }
        });
    }

    private boolean isSelectSeatsForAllTravellers() {
        boolean result = true;
        for(String travellerSeatString: travellerTabStringArray) {
            if(travellerSeatString.isEmpty()) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void initBackButton() {
        binding.selectSeatBackButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void changeSelectedSeatToAvailable() {
        int row = selectedAirplaneSeat.getSeatRow();
        char column = selectedAirplaneSeat.getSeatColumn();
        if(column == 'A') {
            seatsAdapterA.setSeatToAvailable(row - 1);
        }
        else if(column == 'B') {
            seatsAdapterB.setSeatToAvailable(row - 1);
        }
        else if(column == 'C') {
            seatsAdapterC.setSeatToAvailable(row - 1);
        }
        else if(column == 'D') {
            seatsAdapterD.setSeatToAvailable(row - 1);
        }
    }

    private void initSeatRecyclerViewD() {
        // Init data
        initSeatListD();
        // Init adapter
        seatsAdapterD = new AirplaneSeatsAdapter(
                seatListD,
                new AirplaneSeatsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AirplaneSeat item) {
                        int currentStateId = item.getBackgroundResource();
                        if(currentStateId == R.drawable.booked_seat) {
                            Toast.makeText(SelectSeatActivity.this, "This seat is booked", Toast.LENGTH_SHORT).show();
                        }
                        else if(currentStateId == R.drawable.avail_seat) {
                            // Set selected seat to available
                            changeSelectedSeatToAvailable();
                            // Select the new seat
                            seatsAdapterD.setSelectedSeat(item);
                            // Update selected seat
                            selectedAirplaneSeat = item;
                            setTravellerAndSeatText();
                        }
                    }
                }
        );
        binding.dSeatsRecyclerView.setLayoutManager(new LinearLayoutManager(
                SelectSeatActivity.this,
                LinearLayoutManager.VERTICAL, false));
        binding.dSeatsRecyclerView.setAdapter(seatsAdapterD);
    }

    private void initSeatListD() {
        seatListD = new ArrayList<>();
        seatListD.add(new AirplaneSeat(1, 'D', R.drawable.avail_seat));
        seatListD.add(new AirplaneSeat(2, 'D', R.drawable.booked_seat));
        seatListD.add(new AirplaneSeat(3, 'D', R.drawable.avail_seat));
        seatListD.add(new AirplaneSeat(4, 'D', R.drawable.avail_seat));
        seatListD.add(new AirplaneSeat(5, 'D', R.drawable.avail_seat));
        seatListD.add(new AirplaneSeat(6, 'D', R.drawable.booked_seat));
        seatListD.add(new AirplaneSeat(7, 'D', R.drawable.avail_seat));
    }

    private void initSeatRecyclerViewC() {
        // Init data
        initSeatListC();
        // Init adapter
        seatsAdapterC = new AirplaneSeatsAdapter(
                seatListC,
                new AirplaneSeatsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AirplaneSeat item) {
                        int currentStateId = item.getBackgroundResource();
                        if(currentStateId == R.drawable.booked_seat) {
                            Toast.makeText(SelectSeatActivity.this, "This seat is booked", Toast.LENGTH_SHORT).show();
                        }
                        else if(currentStateId == R.drawable.avail_seat) {
                            // Set selected seat to available
                            changeSelectedSeatToAvailable();
                            // Select the new seat
                            seatsAdapterC.setSelectedSeat(item);
                            // Update selected seat
                            selectedAirplaneSeat = item;
                            setTravellerAndSeatText();
                        }
                    }
                }
        );
        binding.cSeatsRecyclerView.setLayoutManager(new LinearLayoutManager(
                SelectSeatActivity.this,
                LinearLayoutManager.VERTICAL, false));
        binding.cSeatsRecyclerView.setAdapter(seatsAdapterC);
    }

    private void initSeatListC() {
        seatListC = new ArrayList<>();
        seatListC.add(new AirplaneSeat(1, 'C', R.drawable.booked_seat));
        seatListC.add(new AirplaneSeat(2, 'C', R.drawable.avail_seat));
        seatListC.add(new AirplaneSeat(3, 'C', R.drawable.avail_seat));
        seatListC.add(new AirplaneSeat(4, 'C', R.drawable.booked_seat));
        seatListC.add(new AirplaneSeat(5, 'C', R.drawable.avail_seat));
        seatListC.add(new AirplaneSeat(6, 'C', R.drawable.booked_seat));
        seatListC.add(new AirplaneSeat(7, 'C', R.drawable.avail_seat));
    }

    private void initSeatRecyclerViewB() {
        // Init data
        initSeatListB();
        // Init adapter
        seatsAdapterB = new AirplaneSeatsAdapter(
                seatListB,
                new AirplaneSeatsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AirplaneSeat item) {
                        int currentStateId = item.getBackgroundResource();
                        if(currentStateId == R.drawable.booked_seat) {
                            Toast.makeText(SelectSeatActivity.this, "This seat is booked", Toast.LENGTH_SHORT).show();
                        }
                        else if(currentStateId == R.drawable.avail_seat) {
                            // Set selected seat to available
                            changeSelectedSeatToAvailable();
                            // Select the new seat
                            seatsAdapterB.setSelectedSeat(item);
                            // Update selected seat
                            selectedAirplaneSeat = item;
                            setTravellerAndSeatText();
                        }
                    }
                }
        );
        binding.bSeatsRecyclerView.setLayoutManager(new LinearLayoutManager(
                SelectSeatActivity.this,
                LinearLayoutManager.VERTICAL, false));
        binding.bSeatsRecyclerView.setAdapter(seatsAdapterB);
    }

    private void initSeatListB() {
        seatListB = new ArrayList<>();
        seatListB.add(new AirplaneSeat(1, 'B', R.drawable.booked_seat));
        seatListB.add(new AirplaneSeat(2, 'B', R.drawable.avail_seat));
        seatListB.add(new AirplaneSeat(3, 'B', R.drawable.avail_seat));
        seatListB.add(new AirplaneSeat(4, 'B', R.drawable.avail_seat));
        seatListB.add(new AirplaneSeat(5, 'B', R.drawable.avail_seat));
        seatListB.add(new AirplaneSeat(6, 'B', R.drawable.booked_seat));
        seatListB.add(new AirplaneSeat(7, 'B', R.drawable.booked_seat));
    }

    private void initSeatRecyclerViewA() {
        // Init data
        initSeatListA();
        // Init adapter
        seatsAdapterA = new AirplaneSeatsAdapter(
                seatListA,
                new AirplaneSeatsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AirplaneSeat item) {
                        int currentStateId = item.getBackgroundResource();
                        if(currentStateId == R.drawable.booked_seat) {
                            Toast.makeText(SelectSeatActivity.this, "This seat is booked", Toast.LENGTH_SHORT).show();
                        }
                        else if(currentStateId == R.drawable.avail_seat) {
                            // Set selected seat to available
                            changeSelectedSeatToAvailable();
                            // Select the new seat
                            seatsAdapterA.setSelectedSeat(item);
                            // Update selected seat
                            selectedAirplaneSeat = item;
                            setTravellerAndSeatText();
                        }
                    }
                }
        );
        binding.aSeatsRecyclerView.setLayoutManager(new LinearLayoutManager(
                SelectSeatActivity.this,
                LinearLayoutManager.VERTICAL, false));
        binding.aSeatsRecyclerView.setAdapter(seatsAdapterA);
    }

    private void initSeatListA() {
        seatListA = new ArrayList<>();
        seatListA.add(new AirplaneSeat(1, 'A', R.drawable.booked_seat));
        seatListA.add(new AirplaneSeat(2, 'A', R.drawable.selected_seat));
        seatListA.add(new AirplaneSeat(3, 'A', R.drawable.booked_seat));
        seatListA.add(new AirplaneSeat(4, 'A', R.drawable.avail_seat));
        seatListA.add(new AirplaneSeat(5, 'A', R.drawable.avail_seat));
        seatListA.add(new AirplaneSeat(6, 'A', R.drawable.booked_seat));
        seatListA.add(new AirplaneSeat(7, 'A', R.drawable.avail_seat));
    }

    private void initTravellerTabList() {
        travellerTabStringArray = new String[FlightTicketUtils.adultsNum];
        travellerTabList = new ArrayList<>();
        for(int i = 0; i < FlightTicketUtils.adultsNum; i++) {
            TravellerTab travellerTab = new TravellerTab(i+1, i == 0);
            travellerTabList.add(travellerTab);
            travellerTabStringArray[i] = "";
        }
    }

    private void initTravellerTabsReyclerView() {
        // Init data
        initTravellerTabList();
        // Init adapter
        travellerTabsAdapter = new TravellerTabsAdapter(
                travellerTabList,
                new TravellerTabsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TravellerTab item) {
                        int currentTravellerIndex = travellerTabsAdapter.getSelectedIndex();
                        if(currentTravellerIndex != item.getTravellerNumber() - 1) {
                            // Save seat for current traveller
                            String seatCode = String.valueOf(selectedAirplaneSeat.getSeatRow())
                                    + selectedAirplaneSeat.getSeatColumn();
                            travellerTabStringArray[currentTravellerIndex] = seatCode;
                            // Change traveller tab
                            int index = item.getTravellerNumber() - 1;
                            travellerTabsAdapter.setActiveTravellerTab(index);
                            // Find another default seat for new traveller
                            String existingSeatCode = travellerTabStringArray[index];
                            if(existingSeatCode.isEmpty()) {
                                chooseSelectedSeatForNewTraveller();
                            }
                            else {
                                goBackToSelectedSeatOfNewTraveller(existingSeatCode);
                            }
                            // Update action text for new traveller
                            setTravellerAndSeatText();
                        }
                    }
                }, 0
        );
        binding.travellerTabsRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                SelectSeatActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        binding.travellerTabsRecyclerView.setAdapter(travellerTabsAdapter);
    }

    private void goBackToSelectedSeatOfNewTraveller(String existingSeatCode) {
        int row = Integer.parseInt(String.valueOf(existingSeatCode.charAt(0)));
        char column = existingSeatCode.charAt(1);
        if(column == 'A') {
            selectedAirplaneSeat = seatsAdapterA.getItem(row - 1);
        }
        else if(column == 'B') {
            selectedAirplaneSeat = seatsAdapterB.getItem(row - 1);
        }
        else if(column == 'C') {
            selectedAirplaneSeat = seatsAdapterC.getItem(row - 1);
        }
        else if(column == 'D') {
            selectedAirplaneSeat = seatsAdapterD.getItem(row - 1);
        }
    
    }

    private void chooseSelectedSeatForNewTraveller() {
        int index = seatsAdapterA.findAvailableSeat();
        if(index != -1) {
            seatsAdapterA.setSelectedSeat(index);
            selectedAirplaneSeat = seatsAdapterA.getItem(index);
            return;
        }
        index = seatsAdapterB.findAvailableSeat();
        if(index != -1) {
            seatsAdapterB.setSelectedSeat(index);
            selectedAirplaneSeat = seatsAdapterB.getItem(index);
            return;
        }
        index = seatsAdapterC.findAvailableSeat();
        if(index != -1) {
            seatsAdapterC.setSelectedSeat(index);
            selectedAirplaneSeat = seatsAdapterC.getItem(index);
            return;
        }
        index = seatsAdapterD.findAvailableSeat();
        if(index != -1) {
            seatsAdapterD.setSelectedSeat(index);
            selectedAirplaneSeat = seatsAdapterD.getItem(index);
        }
    }

    private void setTravellerAndSeatText() {
        // TODO: Change the traveller according to traveller tab
        int selectedTravellerIndex = travellerTabsAdapter.getSelectedIndex();
        String travellerAndSeatText =
                "Traveller " + String.valueOf(selectedTravellerIndex + 1) +
                " / Seat " +
                String.valueOf(selectedAirplaneSeat.getSeatRow()) + selectedAirplaneSeat.getSeatColumn();
        binding.travellerAndSeat.setText(travellerAndSeatText);
    }
}