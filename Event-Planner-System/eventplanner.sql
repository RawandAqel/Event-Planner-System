-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 18, 2024 at 02:18 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eventplanner`
--

-- --------------------------------------------------------

--
-- Table structure for table `event_category`
--

CREATE TABLE `event_category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `event_category`
--

INSERT INTO `event_category` (`id`, `name`) VALUES
(1, 'birthday'),
(2, 'wedding');

-- --------------------------------------------------------

--
-- Table structure for table `event_info`
--

CREATE TABLE `event_info` (
  `id` int(11) NOT NULL,
  `organizer_id` int(11) DEFAULT NULL,
  `event_category_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `event_date` date DEFAULT NULL,
  `event_time` time DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `no_visitor` int(11) DEFAULT NULL,
  `price_per_person` float NOT NULL,
  `no_meals` int(11) NOT NULL,
  `meal_price` float NOT NULL,
  `no_drinks` int(11) NOT NULL,
  `drink_price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `event_info`
--

INSERT INTO `event_info` (`id`, `organizer_id`, `event_category_id`, `name`, `event_date`, `event_time`, `description`, `no_visitor`, `price_per_person`, `no_meals`, `meal_price`, `no_drinks`, `drink_price`) VALUES
(1, 6, 1, 'Rawand birthday', '2024-03-06', '18:09:00', 'Welcome all.', 400, 10, 350, 15, 350, 3),
(4, 4, 2, 'awww', '2024-03-06', '18:09:00', 'ssss', 500, 4, 700, 2, 900, 3),
(5, 4, 1, 'aa', '2024-03-25', '04:08:15', 'sssaaww', 1, 2, 4, 3, 1, 5),
(7, 7, 1, 'QQ', '2024-07-13', '15:30:00', 'eeeeee', 500, 0, 550, 0, 600, 0);

-- --------------------------------------------------------

--
-- Table structure for table `event_meta`
--

CREATE TABLE `event_meta` (
  `id` int(11) NOT NULL,
  `event_info_id` int(11) DEFAULT NULL,
  `link` varchar(1024) DEFAULT NULL,
  `type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `event_meta`
--

INSERT INTO `event_meta` (`id`, `event_info_id`, `link`, `type`) VALUES
(1, 4, 'aabbcc', 1),
(2, 4, 'abcdefg', 2),
(3, 5, 'qqwwee', 1),
(4, 4, 'hhhh', 1),
(5, 4, 'kkkkkkk', 1),
(6, 7, 'dsgfhfggfaSFD', 1);

-- --------------------------------------------------------

--
-- Table structure for table `event_place_order`
--

CREATE TABLE `event_place_order` (
  `id` int(11) NOT NULL,
  `event_info_id` int(11) DEFAULT NULL,
  `place_id` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `event_place_order`
--

INSERT INTO `event_place_order` (`id`, `event_info_id`, `place_id`, `start_date`, `end_date`, `start_time`, `end_time`) VALUES
(1, 4, 3, '2024-03-19', '2024-03-19', '12:08:08', '22:08:08'),
(2, 5, 3, '2024-03-21', '2024-03-23', '08:10:40', '02:11:40'),
(3, 7, 3, '2024-03-19', '2024-03-23', '08:10:40', '02:11:40'),
(4, 1, 3, '2024-03-18', '2024-03-18', '15:30:00', '23:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `organizer`
--

CREATE TABLE `organizer` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `organizer`
--

INSERT INTO `organizer` (`id`, `user_id`) VALUES
(4, 15),
(6, 20),
(7, 23);

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE `place` (
  `id` int(11) NOT NULL,
  `vendor_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `Price_per_hour` float DEFAULT NULL,
  `rate` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `place`
--

INSERT INTO `place` (`id`, `vendor_id`, `name`, `location`, `capacity`, `Price_per_hour`, `rate`) VALUES
(3, 7, 'Aya', 'Nablus', 600, 1500, 5);

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `id` int(11) NOT NULL,
  `visits_order_id` int(11) DEFAULT NULL,
  `total_price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `image` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_name`, `first_name`, `last_name`, `email`, `password`, `image`) VALUES
(1, 'rawand_aqel', 'Rawand', 'Aqel', 'rawand@gmail.com', 'rawand12345', 'q'),
(5, 'RR1', 'Ahmad', 'mohammad', 'Ahmad@gmail.com', 'ahmad12345', 'Ah'),
(14, 'vendor', 'Ahmad', 'mohammad', 'Ahmad@gmail.com', 'ahmad12345', 'Ah'),
(15, 'ORGA', 'Ah', 'Mh', 'Ah@gmail.com', 'ahmad11111', 'asdfgh'),
(16, 'Vis', 'Ahmad', 'mohammad', 'Ahmad@gmail.com', 'ahmad12345', 'Ah'),
(20, 'Moh_1', 'Mohammad', 'Ali', 'Moh@gmail.com', 'moh12345', '12qa'),
(21, 'qq', 'ww', 'ss', 'ff@gmail.com', 'qq11111', 'ssssdddd'),
(22, 'ttt', 'jj', 'ddd', 'qqq@gmail.com', 'aaaa1111', 'ggffssaa'),
(23, 'Iiii', 'Aya', 'Aya', 'sfg@gmail.com', 'Aya11111', 'qwerwtewasd');

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`id`, `user_id`) VALUES
(7, 14);

-- --------------------------------------------------------

--
-- Table structure for table `visitor`
--

CREATE TABLE `visitor` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `visitor`
--

INSERT INTO `visitor` (`id`, `user_id`) VALUES
(1, 16),
(3, 21),
(5, 22);

-- --------------------------------------------------------

--
-- Table structure for table `visits_order`
--

CREATE TABLE `visits_order` (
  `id` int(11) NOT NULL,
  `visitor_id` int(11) DEFAULT NULL,
  `event_info_id` int(11) DEFAULT NULL,
  `no_persons` int(11) DEFAULT NULL,
  `VIP` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `visits_order`
--

INSERT INTO `visits_order` (`id`, `visitor_id`, `event_info_id`, `no_persons`, `VIP`) VALUES
(1, 1, 4, 5, 0),
(2, 5, 5, 1, 1),
(4, 3, 4, 2, 1),
(5, 3, 7, 5, 0),
(6, 1, 7, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event_category`
--
ALTER TABLE `event_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `event_info`
--
ALTER TABLE `event_info`
  ADD PRIMARY KEY (`id`),
  ADD KEY `organizer_id` (`organizer_id`),
  ADD KEY `event_category_id` (`event_category_id`);

--
-- Indexes for table `event_meta`
--
ALTER TABLE `event_meta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_info_id` (`event_info_id`);

--
-- Indexes for table `event_place_order`
--
ALTER TABLE `event_place_order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_info_id` (`event_info_id`),
  ADD KEY `place_id` (`place_id`);

--
-- Indexes for table `organizer`
--
ALTER TABLE `organizer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `place`
--
ALTER TABLE `place`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vendor_id` (`vendor_id`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `visits_order_id` (`visits_order_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `visitor`
--
ALTER TABLE `visitor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `visits_order`
--
ALTER TABLE `visits_order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `visitor_id` (`visitor_id`),
  ADD KEY `event_info_id` (`event_info_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event_category`
--
ALTER TABLE `event_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `event_info`
--
ALTER TABLE `event_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `event_meta`
--
ALTER TABLE `event_meta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `event_place_order`
--
ALTER TABLE `event_place_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `organizer`
--
ALTER TABLE `organizer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `place`
--
ALTER TABLE `place`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `visitor`
--
ALTER TABLE `visitor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `visits_order`
--
ALTER TABLE `visits_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `event_info`
--
ALTER TABLE `event_info`
  ADD CONSTRAINT `event_info_ibfk_1` FOREIGN KEY (`organizer_id`) REFERENCES `organizer` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `event_info_ibfk_2` FOREIGN KEY (`event_category_id`) REFERENCES `event_category` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `event_meta`
--
ALTER TABLE `event_meta`
  ADD CONSTRAINT `event_meta_ibfk_1` FOREIGN KEY (`event_info_id`) REFERENCES `event_info` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `event_place_order`
--
ALTER TABLE `event_place_order`
  ADD CONSTRAINT `event_place_order_ibfk_1` FOREIGN KEY (`event_info_id`) REFERENCES `event_info` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `event_place_order_ibfk_2` FOREIGN KEY (`place_id`) REFERENCES `place` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `organizer`
--
ALTER TABLE `organizer`
  ADD CONSTRAINT `organizer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `place`
--
ALTER TABLE `place`
  ADD CONSTRAINT `place_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`visits_order_id`) REFERENCES `visits_order` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `vendor`
--
ALTER TABLE `vendor`
  ADD CONSTRAINT `vendor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `visitor`
--
ALTER TABLE `visitor`
  ADD CONSTRAINT `visitor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `visits_order`
--
ALTER TABLE `visits_order`
  ADD CONSTRAINT `visits_order_ibfk_1` FOREIGN KEY (`visitor_id`) REFERENCES `visitor` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `visits_order_ibfk_2` FOREIGN KEY (`event_info_id`) REFERENCES `event_info` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
