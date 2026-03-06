# System Flow Diagram

```text
                        START
                          │
                          ▼
                User Login / Signup
                          │
                          ▼
                     Create Trip
                          │
                          ▼
                 Enter Trip Information
     (Current Location, Destination, Days,
      People, Budget, Preferences, Trip Type)
                          │
                          ▼
                 Trip Planning Engine
                          │
                          ▼
                Budget Feasibility Check
                          │
              ┌───────────┴───────────┐
              ▼                       ▼
        Budget Sufficient?          NO
              │                      │
             YES                     ▼
              │           Suggest Minimum Budget
              │           or Modify Preferences
              │                      │
              └───────────────┬──────┘
                              ▼
                        User Decision
                 (Modify or Continue Plan)
                              │
                              ▼
                       Weather Analyzer
                              │
                              ▼
                     Severe Weather?
                 ┌────────────┴────────────┐
                 ▼                         ▼
                YES                       NO
                 │                         │
     Suggest Alternative Time/Location     │
                 │                         │
                 └─────────────┬───────────┘
                               ▼
                       Trip Type Analyzer
                 (Tourism / Pilgrimage / Mixed)
                               │
                               ▼
                     Temple Crowd Analyzer
                               │
                               ▼
                   Place Recommendation Engine
                               │
                               ▼
                       Route Optimization
                 (Minimize Travel Distance)
                               │
                               ▼
                      Generate Itinerary
                   (Day-wise Travel Plan)
                               │
                               ▼
                 Suggest Hotels / Transport
                               │
                               ▼
                     Suggest Local Guides
                               │
                               ▼
                      Booking Assistance
                               │
                               ▼
                        Trip Execution
                               │
                               ▼
                         Expense Tracker
                               │
                               ▼
                       Expense Split Engine
                               │
                               ▼
                          Trip Finished
                               │
                               ▼
                           Archive Trip
                               │
                               ▼
                               END
```
