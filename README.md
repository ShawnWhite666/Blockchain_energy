# Blockchain_energy

## Project Overview

Traditional blockchain-based energy trading systems face challenges such as long processing times and slow settlements. To address these issues, we developed a Lightweight Blockchain Database System, a Java-based microservices architecture. This innovative system integrates databases and caching technologies to revolutionize traditional blockchain transaction systems, resulting in a high-performance energy trading platform based on a private blockchain.

## Personal Responsibilities

**System Design**

- Pioneered an innovative "off-chain settlement, on-chain storage" approach to overcome the slow settlement issues of traditional blockchain projects.
- Implemented an asynchronous architecture combining databases, caching, and blockchain technologies.
- Achieved separation of hot and cold data, maintaining blockchain security while introducing high-performance characteristics of internet technologies.

**Order Matching Algorithm and Process Design**

- Developed a time-segmented processing approach.
- Implemented a rolling knapsack algorithm for order matching at specific time intervals.
- Designed a priority system based on time, price, and credit for efficient sorting.
- Introduced cache preheating for sorting results, significantly improving matching performance and solving issues of difficult electricity buy-sell matching.

**Key Achievements and Innovations**

High Performance:
- Simplified blockchain infrastructure using a custom-built private chain.
- Separated business logic from blockchain operations using asynchronous design.
- Integrated Redis and MySQL with blockchain technology for efficient hot/cold data separation.
- Significantly reduced business processing time.
- Implemented a rolling knapsack algorithm for buy-sell matching, reducing time complexity to O(NlogN).
- Utilized cache preheating for sorting results.
- Achieved millisecond-level response times for user transactions and second-level block generation.

Outstanding Results:
- Published 20 high-quality research papers.
- Filed 21 domestic and international patents (9 granted).
- Obtained 16 software copyrights.
- Won the National First Prize in the "Challenge Cup" Science and Technology Competition.
- Received two provincial awards in the "Internet+" Competition in Shaanxi.

This project demonstrates a significant advancement in blockchain technology for energy trading, combining the security of blockchain with the high performance of traditional database systems. It showcases innovative problem-solving in the fields of distributed systems, algorithm design, and performance optimization.

## Runtime Demonstration

![image-20240810165227178](img/image-20240810165227178.png)

