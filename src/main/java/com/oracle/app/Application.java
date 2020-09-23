package com.oracle.app;

import com.oracle.app.services.*;

public class Application {
    public static void main(String[] args) {
		VendingHarness vm = new VendingHarness();
		vm.menu();
    }
}