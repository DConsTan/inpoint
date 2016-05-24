package nl.tudelft.inpoint;

import android.net.wifi.ScanResult;

import java.util.Comparator;

public class ScanResultComparator implements Comparator<ScanResult> {
    @Override
    public int compare(ScanResult t0, ScanResult t1) {
        return t0.level - t1.level;
    }
}
