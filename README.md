![University](https://www.iade.europeia.pt/hs-fs/hubfs/IADE-SITE/static/ue-iade-h75.png?width=444&name=ue-iade-h75.png)

# pacaward - Project Report

## Computer Engineering 2019/2020 

Student Name | Student Number | Contact
------------ | ------------- | -------------
Francisco Cordeiro | 50037301 | franciscordeiro99@gmail.com

Proposal made by

Name | Contact 
------------ | ------------- 
André Elias | andre@fidel.uk 


## Context

Consumers often have the problem of receiving discount offers for things they do not really need. Moreover, retailers also have problems when it comes to targeting the right customers for their discounts.
Card-linked offers (CLO) is a Online-to-Offline (O2O) system, meaning the online marketing leads to an offline (physical) experience, helping mitigate the problems for both entities. Firstly by getting consumers behavior data (with their consent), analysing it, enabling the retailers to make specific marketing campaigns. Secondly by having personalized offers to each consumer, applying the discounts automatically on each purchase just by using the credit or debit card, discarding the need for coupons, promo codes or membership cards.
This way consumers avoid offers for merchants they do not really need, leaving only offers to what they are looking for and turns digital banking into a marketing channel for retailers, benefiting both parts.

Pacaward is a CLO Android application that integrates Fidel API (Application Programming Interface) and Fidel SDK (Software Development Kit). The app integrates Fidel SDK so the user can link debit/credit cards to the app. When one of these linked cards is used to complete a purchase that has a discount available on the app, this discount is redeemed automatically and the app sends a transaction notification to the user, which is also made visible in the app.
Using Fidel tools is a great benefit to this project since they already passed difficult barriers caused by the need of accessing customer payment data, consequently there is no need to deal with sensitive data. 

CLO is a booming market as a result of the shift to cashless (growth of 20% per year), thus there are many apps related to this, the most relevant being:
* Acorns¹ (https://www.acorns.com/) - in every purchase with a liked-card, rounds up to next dollar, giving the user a choice to save or invest their change.</li>
* Dosh² (https://www.dosh.cash/) - partner of more than 1000 stores and restaurants, has a referral system where users can earn money when a friend links a card.</li> 
* Drop³ (https://www.earnwithdrop.com/) - works with points instead of direct cashback.</li>

Although there are many CLO applications like the ones presented before, all applications differ in their partners, meaning there will be distinct retailers, therefore different offers. Apps also differ in the way they offer their discounts, some work with direct cashback, others use a points system forcing the users to spend it on other offers.


## Scenarios
### Primary scenario

The user sees a list of locations with offers available, links new cards, sees a list of his linked cards and the transactions he has made.

1. When entering the app, the user has a list of all offers available;
2. By clicking on the Profile user is redirected to profile screen, where by display is showed the “Cards” sections;
3. By clicking in the plus sign to add a new card and is redirected to add card page;
4. Fills the information required and submits;
5. Is redirected to the “Cards” section where the new card is already visible;
6. User selects “Transactions” section;
7. It is displayed a list of the transactions made by the user.


### Secondary scenarios

#### 1st.
User makes a transaction with a linked card and receives a push notification.
 
#### 2nd.
User is on the home screen, clicks on an offer and it is expanded, showing detailed information and a location preview.

## Personas
User - every user must have an account and has access to all functionalities.

## Work plan
![WorkPlan](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/workplanv2.jpg)

## Requirements

### Functional Requirements
![Functional Requirements](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/FR.jpg)

### Non-Functional Requirements
![Non-Functional Requirements](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/NFR.jpg)

## Domain Model
![Domain Model](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/DomainModel.jpg)
## Mockups

![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/001.Splash%20screen.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/002.Initial%20screen.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/003.Login%20screen.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/004.Sign%20up%20screen.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/005.Main%20page.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/007.Main%20page%20–%20detailed%20offer.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/008.Transactions.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/009.Cards[1].png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/011.Cards[2].png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/012.Card%20Delete.png)
![Mockups](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups/006.Log%20out.png)

[Interactive Mockups](https://xd.adobe.com/view/fb51c454-218f-44e2-6668-dec95376cf5d-8d60/?fullscreen=off&hints=on)

### Mockups flow
![Mockups flow](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/mockups_flow.png)

## WBS
![WBS](https://raw.githubusercontent.com/c0rdeiro/pacaward/master/Deliverables/Attachments/wbs.png)
