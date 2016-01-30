package org.silnith.browser.model;

public class NavigationResult {
    
    private final NavigationRequest navigationRequest;
    
    public NavigationResult(final NavigationRequest navigationRequest) {
        super();
        this.navigationRequest = navigationRequest;
    }
    
    public NavigationRequest getNavigationRequest() {
        return navigationRequest;
    }
    
}
