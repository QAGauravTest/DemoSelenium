Feature: Ecomm Price Comparison

  Scenario Outline: iPhone 14 pro Price Lists
    Given User launches browser "<browser>" and opens "<Ecomm>" URL "<url>"
    When User Searches for product "iphone 14 pro max"
#    And Verifies if any broken link is present
    Then User captures list of prices and exports in Excel
    And stores the cheapest price with Ecomm name in Excel
    And stores the VERDICT with status as Cheapest
    And User closes the browser

    Examples:
    |browser|              url          |Ecomm   |
    |edge   |https://www.flipkart.com/  |Flipkart  |
    #|edge   |https://www.amazon.in/    | Amazon  |
    #|chrome   |https://www.amazon.in/   | Amazon |
