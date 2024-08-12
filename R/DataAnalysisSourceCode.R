#-----DATA EXPLORATION-----


#read file
theDataSet <- read.csv("House_Rent_Dataset.csv",header=TRUE)
View(theDataSet)

#assigning headers
names(theDataSet)=c("DatePosted", "Bedroom", "Price", "Size", "Level", "AreaType", "Region", "City", "Furniture", "Preference", "ShowerRoom", "ContactTo")
theDataSet
View(theDataSet)

#summary stats for all variables in given data set
head(theDataSet)
summary(theDataSet)

#check missing value
is.na(theDataSet)
sum(is.na(theDataSet))

#check duplicate data
sum(duplicated(theDataSet))

#bachelor 
bachelor <- filter(theDataSet, Preference == "Bachelors" )
bachelor

#Bachelors/family
bachelor_family <- filter(theDataSet, Preference == "Bachelors/Family" )
bachelor_family

#family 
family <- filter(theDataSet, Preference == "Family" )
family





#-----DATA VISUALIZATION-----



library(ggplot2)

#which city is the most ordered?
ggplot(theDataSet, aes(City))+geom_bar(aes(fill = AreaType))

#What are the characteristics of Bachelors?
summary(bachelor)


#Which city do the most bachelors book based on the number of Shower Rooms?
ggplot(bachelor, aes(City))+geom_bar(aes(fill = ShowerRoom))

#What type of furniture is most dominantly ordered by Bachelor based on price?
ggplot(bachelor, aes(Furniture))+geom_bar(aes(fill = Price ))

#what area types booked by Bachelors?
ggplot(bachelor, aes(AreaType))+geom_bar(aes(fill = Preference ))

#Which contact preference do Bachelors reach out to?
ggplot(bachelor, aes(ContactTo))+geom_bar(aes(fill = Preference ))

#How many bedrooms do bachelors usually book?
ggplot(bachelor, aes(Bedroom))+geom_bar(aes(fill = Preference ))

#Which city is the most ordered by bachelors to book?
ggplot(bachelor, aes(City))+geom_bar(aes(fill = Preference ))

#Which city is preferred by Bachelor based on the accommodation size?
library(ggridges)
ggplot(bachelor, 
       aes(x = Size, 
           y = City, 
           fill = Size)) +
  geom_density_ridges() + 
  theme_ridges() +
  labs("Highway mileage by auto class") +
  theme(legend.position = "none")

#What area type is most ordered by Bachelor based on price?
ggplot(data = bachelor)+ 
  geom_point(mapping = aes(x = Price , y = AreaType, size = Preference))

#What is the 1st,2nd , and 3rd quartal of the cities ordered by Bachelor?
ggplot(bachelor, 
       aes(x = City, 
           y = Size)) +
  geom_boxplot() +
  labs(title = "Quartal 1 - 3 City")

#What furnishings is ordered by bachelors based on price?
ggplot(bachelor, 
       aes(x=Price, 
           y=reorder(Furniture, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(Furniture, Price), 
                   yend = reorder(Furniture, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "",
        title = "furniture type by room price",)
+
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())

#What furnishings is the most expensive ordered by bachelor?
ggplot(bachelor, 
       aes(x=Price, 
           y=reorder(Furniture, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(Furniture, Price), 
                   yend = reorder(Furniture, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "Furniture",
        title = "Hotel room rates",
        subtitle = "furniture type") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())

#What type of area is the most expensive bachelor tenant book?
ggplot(bachelor, 
       aes(x=Price, 
           y=reorder(AreaType, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(AreaType, Price), 
                   yend = reorder(AreaType, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "area type",
        title = "Hotel room rates",
        subtitle = "Type area") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())


#Are the average contact agents booked for expensive accommodation by bachelors ?
ggplot(bachelor, 
       aes(x=Price, 
           y=reorder(ContactTo, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(ContactTo, Price), 
                   yend = reorder(ContactTo, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "Contact",
        title = "Hotel room rates",
        subtitle = "Type contact") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())




#====================================================================






#what are the characteristics of bachelors/family tenants?

summary(bachelor_family)

#Which city is most dominantly ordered by bachelors/families ? 
ggplot(bachelor_family, aes(City))+geom_bar(aes(fill = Preference))

#What furnishings is most ordered by Bachelor/Family?
ggplot(bachelor_family, aes(Furniture))+geom_bar(aes(fill = Preference ))

#What area type do most Bachelor/Family tenants book?
ggplot(bachelor_family, aes(AreaType))+geom_bar(aes(fill = Preference ))

#What do Bachelor/Family tenants order through?
ggplot(bachelor_family, aes(ContactTo))+geom_bar(aes(fill = Preference ))

#What is the dominant number of bedrooms booked by bachelor/family tenants ? 
ggplot(bachelor_family, aes(Bedroom))+geom_bar(aes(fill = Preference ))

#Which city do Bachelor/Family tenants order the most Semi-Furnished?
ggplot(bachelor_family, aes(City))+geom_bar(aes(fill = Furniture ))

#Which contact preference relates to the area type?
library(ggridges)
ggplot(bachelor_family, aes(AreaType))+geom_bar(aes(fill = ContactTo ))

#How does the city relate to the price for Bachelor/Family tenants?
ggplot(bachelor_family, 
       aes(x = Price, 
           y = City, 
           fill = Price)) +
  geom_density_ridges() + 
  theme_ridges() +
  labs("Highway mileage by auto class") +
  theme(legend.position = "none")

#Bachelor/Family tenants pay the most for what area type?
ggplot(data = bachelor_family)+ 
  geom_point(mapping = aes(x = Price , y = AreaType, size = Preference))

#What is the dominant room size for Bachelor/Family tenants based on furnishings?
ggplot(bachelor_family, 
       aes(x = Furniture, 
           y = Size)) +
  geom_boxplot() +
  labs(title = "Size room type furniture")

#What type of city sells the most expensive rooms booked by bachelors/families?
ggplot(bachelor_family, 
       aes(x=Price, 
           y=reorder(City, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(City, Price), 
                   yend = reorder(City, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "City",
        title = "The type of city that sells the most expensive rooms") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())


#====================================================================



#what are the characteristics of family tenants?


#What type of city is the most ordered by Family Tenants?
ggplot(family, aes(City))+geom_bar(aes(fill = Preference))

#What furnishings do Family tenants demand the most?
ggplot(family, aes(Furniture))+geom_bar(aes(fill = Preference ))

#What type of area is most ordered by the Family Tenants?
ggplot(family, aes(AreaType))+geom_bar(aes(fill = Preference ))

#In ordering hotel rooms, family tenants mainly requests by contacting whom?
ggplot(family, aes(ContactTo))+geom_bar(aes(fill = Preference ))

#How many bedrooms have been ordered by tenant families?
ggplot(family, aes(Bedroom))+geom_bar(aes(fill = Preference ))

#Based on the cities, which city are most ordered by contacting agents?
ggplot(family, aes(City))+geom_bar(aes(fill = ContactTo ))


#Based on the type of area, which type of area is the most dominant is ordered by contacting the owner?
ggplot(family, aes(AreaType))+geom_bar(aes(fill = ContactTo ))

#What is the most expensive price based on area type for family tenants?
ggplot(data = family)+ 
  geom_point(mapping = aes(x = Price , y = AreaType, size = Preference))

#What is the dominant size for family tenants based on the furnishings?
ggplot(family, 
       aes(x = Furniture, 
           y = Size)) +
  geom_boxplot() +
  labs(title = "Size room furniture")

#In which city is the most expensive ordered by Family Tenants?
ggplot(family, 
       aes(x=Price, 
           y=reorder(City, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(City, Price), 
                   yend = reorder(City, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "City",
        title = " hotel room rates",
        subtitle = "Type City") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())

#What type of furniture is the most expensive ordered by family tenants?
ggplot(family, 
       aes(x=Price, 
           y=reorder(Furniture, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(Furniture, Price), 
                   yend = reorder(Furniture, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "Furniture",
        title = "Hotel room rates",
        subtitle = "Type furniture") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())

#What area type is the most expensive for family tenants?
ggplot(family, 
       aes(x=Price, 
           y=reorder(AreaType, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(AreaType, Price), 
                   yend = reorder(AreaType, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "area type",
        title = "Hotel room rates",
        subtitle = "Type area") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())


#Which contact point do most family tenants order the most expensive option?
ggplot(family, 
       aes(x=Price, 
           y=reorder(ContactTo, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(ContactTo, Price), 
                   yend = reorder(ContactTo, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "Contact",
        title = "hotel room rates",
        subtitle = "Based contact") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())


#====================================================================


#What are the characteristics of the tenants(overall)? 


#1 What type of furnishings dominantly preferred by different tenants?
ggplot(theDataSet, aes(Furniture))+geom_bar(aes(fill = Preference ))

summary(theDataSet)

#2 What type of area is most dominantly ordered by different tenants
ggplot(theDataSet, aes(AreaType))+geom_bar(aes(fill = Preference ))

#3 What type of contact is mostly reached by different tenants?
ggplot(theDataSet, aes(ContactTo))+geom_bar(aes(fill = Preference ))

#4 What type of bedroom is most dominantly ordered by different tenants?
ggplot(theDataSet, aes(Bedroom))+geom_bar(aes(fill = Preference ))

#5 What type of city is most dominantly ordered by different tenants?
ggplot(theDataSet, aes(City))+geom_bar(aes(fill = Preference ))

#6 what size do different tenants choose?
ggplot(theDataSet,aes(x= Preference,y=Size))+geom_point(aes(color = Size))

#7 What is the relationship between Bachelor and area type?
ggplot(data = theDataSet)+ 
  geom_point(mapping = aes(x = Price , y = AreaType, size = Preference))

#8 What are the most expensive spending tenants in booking?
ggplot(theDataSet, 
       aes(x=Price, 
           y=reorder(Preference, Price))) +
  geom_point(color="blue", 
             size = 2) +
  geom_segment(aes(x = 40, 
                   xend = Price, 
                   y = reorder(Preference, Price), 
                   yend = reorder(Preference, Price)),
               color = "lightgrey") +
  labs (x = "Price",
        y = "Preference",
        title = "hotel room rates",
        subtitle = "Based on Preference") +
  theme_minimal() + 
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank())




#====================================================================



#Analysis Based on the whole Dataset

#1 What type of bedroom count is the best selling?
ggplot(data=theDataSet, aes (x=Bedroom)) +
  geom_histogram(shade=" steelblue ", color=" green ") +
  ggtitle(" Histogram Bedroom ")

#2 What type of rent price is most ordered by tenants?
ggplot(data=theDataSet, aes (x=Price)) +
  geom_histogram(shade=" steelblue ", color=" blue ") +
  ggtitle(" Histogram Price ")

#3 What type of rent size is most ordered by tenants?
ggplot(data=theDataSet, aes (x=Size)) +
  geom_histogram(shade=" steelblue ", color=" yellow ") +
  ggtitle(" Histogram Size ")

#4 Which city is the most ordered?
ggplot(data = theDataSet) + 
  geom_bar(mapping = aes(x = City), color="blue")

#5 What type of furnishing is the most ordered?
ggplot(data = theDataSet) + 
  geom_bar(mapping = aes(x = Furniture), color="blue")

#6 What type of tenants order the most(preference)?
ggplot(data = theDataSet) + 
  geom_bar(mapping = aes(x = Preference), color="green")

#7 Which Shower Room count is the most ordered?
ggplot(data = theDataSet) + 
  geom_bar(mapping = aes(x = ShowerRoom), color="blue")

#8 What is the most point of contact?
ggplot(data = theDataSet) + 
  geom_bar(mapping = aes(x = ContactTo), color="blue")


