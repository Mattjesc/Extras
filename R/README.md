# Comprehensive Data Analysis and Visualization on House Rent Dataset

This project involves exploring, cleaning, manipulating, and visualizing a dataset related to accommodation options in various cities in India. The primary objective is to derive insights and analyze trends that can help understand the rental market better.

## Introduction

The assignment focuses on using R programming for data analysis, utilizing various techniques to clean, manipulate, and visualize the data. The dataset used contains information on accommodation options, including factors such as the number of bedrooms, rent price, furniture type, and city.

## Data Exploration

- **Data Import**: The dataset was imported using the `read.csv` function in R. The data was stored in a CSV file, and appropriate headers were assigned to ensure clarity in the analysis.

- **Data Cleaning**: The data was checked for missing values, duplicates, and other inconsistencies. The dataset was found to be clean, with no missing or duplicate entries.

- **Data Manipulation**: Column headers were renamed for better understanding and ease of analysis. This step was crucial in preparing the dataset for the subsequent analysis.

## Data Visualization

The project involved extensive use of the `ggplot2` package in R for visualizing the data. Key visualizations include:

- **City-wise Orders**: A bar chart showing the most ordered cities.
- **Tenant Preferences**: Visualizations exploring the preferences of different tenant types (Bachelors, Families, Bachelors/Family).
- **Price Distribution**: Histograms displaying the distribution of rent prices across different accommodation options.
- **Furnishing Types**: Analysis of the most commonly ordered furnishing types among tenants.
- **Room Size and Price Correlation**: Scatter plots and box plots to analyze the relationship between room size, price, and tenant preferences.

## Analysis

The analysis was divided into several sections, focusing on different aspects of the dataset:

- **Overall Dataset Analysis**: Insights on popular cities, preferred room sizes, and common price ranges.
- **Tenant-Specific Analysis**: Detailed analysis of preferences and trends among Bachelors, Families, and combined Bachelors/Family tenants.
- **Price Sensitivity**: Examination of how different tenant groups respond to price variations.
- **Furnishing and Area Type Preferences**: Analysis of how the furnishing and area types influence tenant choices.

## Additional Features

Additional features and libraries used in the analysis include:

- **ggridges**: For creating density plots to visualize the distribution of accommodation sizes across different cities.
- **geom_point**: For scatter plots that highlight relationships between continuous variables.
- **theme_minimal**: To give the plots a clean and minimalistic look.
- **geom_boxplot**: To visualize the spread and quartiles of data, providing deeper insights into the distribution of variables.

## Conclusion

The project demonstrated the power of R in handling and analyzing large datasets. By cleaning, manipulating, and visualizing the data, meaningful insights were derived that can be useful for businesses and stakeholders in the rental market. The analysis highlights the importance of data-driven decision-making in optimizing business operations and meeting customer needs.
