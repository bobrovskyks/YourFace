package com.example.hp.yourface;

public class AdapterInternetConnect extends CheckVersion implements VersionApp { // adapter
    public String GetVersion() {
        return (ShowInfo());
    }
}
