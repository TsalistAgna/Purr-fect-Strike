-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 11, 2024 at 05:18 AM
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
-- Database: `purrfect_strike`
--

-- --------------------------------------------------------

--
-- Table structure for table `kucing_image`
--

CREATE TABLE `kucing_image` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `path_image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kucing_image`
--

INSERT INTO `kucing_image` (`id`, `nama`, `path_image`) VALUES
(1, 'Nana', 'Assets\\Images\\Kucing\\1.png'),
(2, 'Ayuda', 'Assets\\Images\\Kucing\\2.png'),
(3, 'Dhinda', 'Assets\\Images\\Kucing\\3.png'),
(4, 'Gerald', 'Assets\\Images\\Kucing\\4.png'),
(5, 'Sky', 'Assets\\Images\\Kucing\\5.png'),
(6, 'Nonem', 'Assets\\Images\\Kucing\\6.png');

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(5) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `score` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `nama`, `score`) VALUES
(2, 'nana', 0),
(3, 'dinda', 95),
(4, 'Dara', 32),
(5, 'Dara', 32),
(6, 'Dara', 32),
(7, 'Dira', 29),
(8, 'Lena', 32),
(9, 'Dira', 22),
(10, 'Ayu', 0),
(11, 'Aryan', 31),
(12, 'Dinda', 16),
(13, 'Sari', 0),
(14, 'Dika', 0),
(15, 'Nana', 0),
(16, 'Nana', 0),
(17, 'ayu', 25),
(18, 'Ayu', 0),
(19, 'Dinda', 0);

-- --------------------------------------------------------

--
-- Table structure for table `sound`
--

CREATE TABLE `sound` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `path_sound` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sound`
--

INSERT INTO `sound` (`id`, `nama`, `path_sound`) VALUES
(1, 'Laser1', 'Assets\\Sounds\\laser1.wav'),
(2, 'Laser1', 'Assets\\Sounds\\laser1.wav');

-- --------------------------------------------------------

--
-- Table structure for table `tikus_image`
--

CREATE TABLE `tikus_image` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `path_image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tikus_image`
--

INSERT INTO `tikus_image` (`id`, `nama`, `path_image`) VALUES
(1, 'Tikus1', 'Assets\\Images\\Tikus\\tikus1.png'),
(2, 'Tikus2', 'Assets\\Images\\Tikus\\tikus2.png'),
(3, 'Tikus3', 'Assets\\Images\\Tikus\\tikus3.png'),
(4, 'Tikus4', 'Assets\\Images\\Tikus\\tikus4.png'),
(5, 'Tikus5', 'Assets\\Images\\Tikus\\tikus5.png'),
(6, 'Tikus6', 'Assets\\Images\\Tikus\\tikus6.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kucing_image`
--
ALTER TABLE `kucing_image`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sound`
--
ALTER TABLE `sound`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tikus_image`
--
ALTER TABLE `tikus_image`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kucing_image`
--
ALTER TABLE `kucing_image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `sound`
--
ALTER TABLE `sound`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tikus_image`
--
ALTER TABLE `tikus_image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
