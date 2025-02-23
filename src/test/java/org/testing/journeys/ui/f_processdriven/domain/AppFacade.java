package org.testing.journeys.ui.f_processdriven.domain;

import org.testing.journeys.ui.f_processdriven.model.Customer;

/**
 * This interface will aggregate the core capabilities of the application.
 */
public interface AppFacade {

    void leaveCustomerContactDetails(Customer customer);

    AppFacade navigateToPage(String page);
}
