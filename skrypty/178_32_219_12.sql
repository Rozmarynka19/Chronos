-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: 178.32.219.12
-- Czas wygenerowania: 31 Maj 2021, 11:52
-- Wersja serwera: 5.6.12
-- Wersja PHP: 5.6.28

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `1213146_fSA523`
--
CREATE DATABASE `1213146_fSA523` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `1213146_fSA523`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `attachments_list`
--

CREATE TABLE IF NOT EXISTS `attachments_list` (
  `Attachment_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Task_ID` int(11) DEFAULT NULL,
  `Attachment_Name` text NOT NULL,
  `Attachment_Mime` text NOT NULL,
  `Attachment_Data` blob NOT NULL,
  PRIMARY KEY (`Attachment_ID`),
  KEY `Task_ID` (`Task_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `bills_list`
--

CREATE TABLE IF NOT EXISTS `bills_list` (
  `Bill_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Item_ID` int(11) NOT NULL,
  `Bill_Recipient` text NOT NULL,
  `Bill_RecipientsBankAccount` text NOT NULL,
  `Bill_TransferTitle` text NOT NULL,
  `Bill_Amount` decimal(35,30) NOT NULL,
  `Bill_Desc` text,
  `Bill_Deadline` datetime DEFAULT NULL,
  PRIMARY KEY (`Bill_ID`),
  KEY `Item_ID` (`Item_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=7 ;

--
-- Zrzut danych tabeli `bills_list`
--

INSERT INTO `bills_list` (`Bill_ID`, `Item_ID`, `Bill_Recipient`, `Bill_RecipientsBankAccount`, `Bill_TransferTitle`, `Bill_Amount`, `Bill_Desc`, `Bill_Deadline`) VALUES
(1, 52, 'jan kowalski abc bvn', '11111111111111111111111111', 'Płatność za FV 44/2019', '4887.000000000000000000000000000000', '', '0000-00-00 00:00:00'),
(3, 54, 'jan kowalski abc bvn', '11111111111111111111111111', 'Płatność za FV 44/2019', '4887.000000000000000000000000000000', '', '0000-00-00 00:00:00'),
(5, 57, 'qweqwe', '13123213213', 'rqwwqerqwewqewq', '123.000000000000000000000000000000', '', '0000-00-00 00:00:00'),
(6, 59, 'test', '21323131231231321', 'test', '123.000000000000000000000000000000', '', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `items_list`
--

CREATE TABLE IF NOT EXISTS `items_list` (
  `Item_ID` int(11) NOT NULL AUTO_INCREMENT,
  `List_ID` int(11) NOT NULL,
  `Item_Name` text NOT NULL,
  `Item_Type` text NOT NULL,
  PRIMARY KEY (`Item_ID`),
  KEY `List_ID` (`List_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=62 ;

--
-- Zrzut danych tabeli `items_list`
--

INSERT INTO `items_list` (`Item_ID`, `List_ID`, `Item_Name`, `Item_Type`) VALUES
(25, 35, 'test', 'Task'),
(45, 33, 'test', 'Task'),
(51, 38, 'test22', 'Task'),
(52, 38, 'kowalskiExample', 'Bill'),
(54, 35, 'kowalskiExample3', 'Bill'),
(56, 35, 'a', 'Task'),
(57, 45, 'eqwewq', 'Bill'),
(58, 45, 'adawda', 'Task'),
(59, 46, 'testBill', 'Bill'),
(60, 46, '', 'Task'),
(61, 46, 'testowe', 'Task');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `registered_users`
--

CREATE TABLE IF NOT EXISTS `registered_users` (
  `User_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` text NOT NULL,
  `User_Password` text NOT NULL,
  `User_Email` text NOT NULL,
  `User_Phone` text NOT NULL,
  `External_User_Name` text NOT NULL,
  `External_User_Email` text NOT NULL,
  `Is_Verified` tinyint(1) NOT NULL,
  `Verification_Code` varchar(4) NOT NULL,
  PRIMARY KEY (`User_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=18 ;

--
-- Zrzut danych tabeli `registered_users`
--

INSERT INTO `registered_users` (`User_ID`, `User_Name`, `User_Password`, `User_Email`, `User_Phone`, `External_User_Name`, `External_User_Email`, `Is_Verified`, `Verification_Code`) VALUES
(1, 'test11', '$2y$10$DvLD8To4WWZycZmtXucO2us/1NhcdoTYzUYhBRhVXWjokeyhEuoSy', 'test@test.pl', '123123123', '', '', 0, ''),
(2, 'test22', '$2y$10$dk9oFiwDAcLoVyU9vHtfGe/BnkRc.y1/FUqsRw9JqCr88f/1lTVnu', 'test22@test.pl', '123412345', '', '', 0, ''),
(11, 'test33', '$2y$10$aWW7KaJHp5.dKxj1SxQANewfKUmQEQndTo98r8c0cBaFU97pAYibG', 'test33@test.pl', '123123123', '', '', 0, ''),
(13, 'test', '$2y$10$1xWsoZ91x1kp.Bwwb946l.ocnwzsdBb9R/nXNcXwudzUHVN7PjMCm', 'kj@zut.edu.pl', '123123123', '', '', 0, ''),
(14, 'user', '$2y$10$i2s.CSLZ9ITNNIHSdwa7euzW58Mtn.62FoWIafQdLNB8UJ13rv6.m', 'kj44387@zut.edu.pl', '123123123', '', '', 0, 'z2aq'),
(17, '', '', '', '', 'Jakub', 'kuchar465@gmail.com', 1, '');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_list`
--

CREATE TABLE IF NOT EXISTS `tasks_list` (
  `Task_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Item_ID` int(11) NOT NULL,
  `Task_Deadline` datetime DEFAULT NULL,
  `Task_Desc` text,
  `Task_Recurring` text,
  `Task_Notification` datetime DEFAULT NULL,
  `Task_Priority` enum('1','2','3') NOT NULL,
  `Task_ID_main_task` int(11) DEFAULT NULL,
  PRIMARY KEY (`Task_ID`),
  KEY `Item_ID` (`Item_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=39 ;

--
-- Zrzut danych tabeli `tasks_list`
--

INSERT INTO `tasks_list` (`Task_ID`, `Item_ID`, `Task_Deadline`, `Task_Desc`, `Task_Recurring`, `Task_Notification`, `Task_Priority`, `Task_ID_main_task`) VALUES
(8, 25, '0000-00-00 00:00:00', 'test', '', '0000-00-00 00:00:00', '1', NULL),
(28, 45, '0000-00-00 00:00:00', 'test', '', '0000-00-00 00:00:00', '3', NULL),
(34, 51, '0000-00-00 00:00:00', 'test22', '', '0000-00-00 00:00:00', '1', NULL),
(35, 56, '0000-00-00 00:00:00', 'a', '', '0000-00-00 00:00:00', '3', NULL),
(36, 58, '0000-00-00 00:00:00', 'awdawdwa', '', '0000-00-00 00:00:00', '', NULL),
(37, 60, '0000-00-00 00:00:00', '3123', '', '0000-00-00 00:00:00', '', NULL),
(38, 61, '0000-00-00 00:00:00', 'test', '', '0000-00-00 00:00:00', '', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `transfers_data`
--

CREATE TABLE IF NOT EXISTS `transfers_data` (
  `Transfer_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) DEFAULT NULL,
  `Transfer_Bank_account_number` text NOT NULL,
  `Transfer_Name_of_recipient` text NOT NULL,
  `Transfer_Title` text NOT NULL,
  `Tranfer_Amount` text NOT NULL,
  PRIMARY KEY (`Transfer_ID`),
  KEY `User_ID` (`User_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `User_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(256) NOT NULL,
  `Password` text NOT NULL,
  `Email` varchar(256) NOT NULL,
  `Phone` varchar(256) NOT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=16 ;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`User_Id`, `Login`, `Password`, `Email`, `Phone`) VALUES
(2, 'test', '$2y$10$VjDKsJGLfA5CfPLrogmKWuFBEsADswXGrHDrh3hbJDdEZaXfqu.XK', 'test@gmail.com', '12345678'),
(3, 'test2', '$2y$10$pVQ5q2kNKoMdFW1XL.ne8eBrsYHhLEvdjXHhrfOR64D2eYX0iOuoK', 'test2@wp.pl', '123456789'),
(4, 'test3', '$2y$10$BOPzmdOwV/bGRhcLOiSOY./s2t/3zVEQBwFBHJcvh5qpVFtqx0Gea', 'test3@gmail.com', '14124423442'),
(8, 'test4', '$2y$10$0yOmofx0EGyfPaWX9BEkhOumbQbbjoaaFRfvr/wNOdDJohfECnGPy', 'test4@gmail.com', '3234234234'),
(9, 'test5', '$2y$10$Horcw6QXD4LDEFmn0D/dI.LeD5ntn2Ufy8obTOIZ1aEhIQ/l9inZy', 'test5@gmail.com', '523234234324'),
(10, 'test8', '$2y$10$hlFRuCGMPoHUKPJMKAoRCukeLfKoeoAXOti23T1AAFPzE8BQAcEH2', 'test8@gmail.com', '34234234234'),
(11, 'test9', '$2y$10$Sk3y6U49QaWgbqvm3cQ1Heb66Sgjp1BhSi19VhP61WZKMgJKMMraK', 'test9@gmail.com', '532423423423'),
(12, 'test10', '$2y$10$eR.IKaR4HH/sGDZ0UxZQ.OUBO6BSGw2Veff/b32iopZCDByTpC35W', 'test10@gmail.com', '52342342342'),
(13, 'test11', '$2y$10$szFLzDxjbbh8PVaxSDtu7.PJhNX/Y5MePTRzu1hIGMvAMTz0kxbbi', 'test11@gw.pl', '34123123'),
(14, 'test12', '$2y$10$0znCy7A65Ps0EWkPQ2Ap6OkWSpzO69Ge.yKrWZnQ6Yq7kqGBhjSCG', 'test12@wp.pl', '1412312312'),
(15, 'test13', '$2y$10$Z9S5YXuHOEibkJQAO3NSXOghL77rBZTW20uWvAs4/qLBng/ZugNBO', 'test13@wp.pl', '123123123');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_lists`
--

CREATE TABLE IF NOT EXISTS `user_lists` (
  `List_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) DEFAULT NULL,
  `List_Name` text NOT NULL,
  PRIMARY KEY (`List_ID`),
  KEY `User_ID` (`User_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=48 ;

--
-- Zrzut danych tabeli `user_lists`
--

INSERT INTO `user_lists` (`List_ID`, `User_ID`, `List_Name`) VALUES
(33, 11, 'praca'),
(35, 11, 'dom'),
(38, 11, 'testowa lista'),
(39, 11, 'rachunki'),
(40, 11, 'test2'),
(45, 13, 'test'),
(46, 14, 'test');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users_preference`
--

CREATE TABLE IF NOT EXISTS `users_preference` (
  `User_preference_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) DEFAULT NULL,
  `User_Notification_Sound_Path` text,
  PRIMARY KEY (`User_preference_ID`),
  KEY `User_ID` (`User_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
