-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 21 Maj 2021, 20:14
-- Wersja serwera: 10.4.11-MariaDB
-- Wersja PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `1213146_fsa523`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `attachments_list`
--

CREATE TABLE `attachments_list` (
  `Attachment_ID` int(11) NOT NULL,
  `Task_ID` int(11) DEFAULT NULL,
  `Attachment_Name` text NOT NULL,
  `Attachment_Mime` text NOT NULL,
  `Attachment_Data` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `bills_list`
--

CREATE TABLE `bills_list` (
  `Bill_ID` int(11) NOT NULL,
  `Item_ID` int(11) NOT NULL,
  `Bill_Recipient` text NOT NULL,
  `Bill_RecipientsBankAccount` text NOT NULL,
  `Bill_TransferTitle` text NOT NULL,
  `Bill_Amount` decimal(35,30) NOT NULL,
  `Bill_Desc` text DEFAULT NULL,
  `Bill_Deadline` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `items_list` (
  `Item_ID` int(11) NOT NULL,
  `List_ID` int(11) NOT NULL,
  `Item_Name` text NOT NULL,
  `Item_Type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `registered_users` (
  `User_ID` int(11) NOT NULL,
  `User_Name` text NOT NULL,
  `User_Password` text NOT NULL,
  `User_Email` text NOT NULL,
  `User_Phone` text NOT NULL,
  `External_User_Name` text NOT NULL,
  `External_User_Email` text NOT NULL,
  `Is_Verified` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `registered_users`
--

INSERT INTO `registered_users` (`User_ID`, `User_Name`, `User_Password`, `User_Email`, `User_Phone`, `External_User_Name`, `External_User_Email`, `Is_Verified`) VALUES
(1, 'test11', '$2y$10$DvLD8To4WWZycZmtXucO2us/1NhcdoTYzUYhBRhVXWjokeyhEuoSy', 'test@test.pl', '123123123', '', '', 0),
(2, 'test22', '$2y$10$dk9oFiwDAcLoVyU9vHtfGe/BnkRc.y1/FUqsRw9JqCr88f/1lTVnu', 'test22@test.pl', '123412345', '', '', 0),
(11, 'test33', '$2y$10$aWW7KaJHp5.dKxj1SxQANewfKUmQEQndTo98r8c0cBaFU97pAYibG', 'test33@test.pl', '123123123', '', '', 0),
(13, 'test', '$2y$10$1xWsoZ91x1kp.Bwwb946l.ocnwzsdBb9R/nXNcXwudzUHVN7PjMCm', 'kj@zut.edu.pl', '123123123', '', '', 0),
(14, 'user', '$2y$10$i2s.CSLZ9ITNNIHSdwa7euzW58Mtn.62FoWIafQdLNB8UJ13rv6.m', 'kj44387@zut.edu.pl', '123123123', '', '', 0),
(17, '', '', '', '', 'Jakub', 'kuchar465@gmail.com', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_list`
--

CREATE TABLE `tasks_list` (
  `Task_ID` int(11) NOT NULL,
  `Item_ID` int(11) NOT NULL,
  `Task_Deadline` datetime DEFAULT NULL,
  `Task_Desc` text DEFAULT NULL,
  `Task_Recurring` text DEFAULT NULL,
  `Task_Notification` datetime DEFAULT NULL,
  `Task_Priority` enum('1','2','3') NOT NULL,
  `Task_ID_main_task` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `transfers_data` (
  `Transfer_ID` int(11) NOT NULL,
  `User_ID` int(11) DEFAULT NULL,
  `Transfer_Bank_account_number` text NOT NULL,
  `Transfer_Name_of_recipient` text NOT NULL,
  `Transfer_Title` text NOT NULL,
  `Tranfer_Amount` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `User_Id` int(11) NOT NULL,
  `Login` varchar(256) NOT NULL,
  `Password` text NOT NULL,
  `Email` varchar(256) NOT NULL,
  `Phone` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
-- Struktura tabeli dla tabeli `users_preference`
--

CREATE TABLE `users_preference` (
  `User_preference_ID` int(11) NOT NULL,
  `User_ID` int(11) DEFAULT NULL,
  `User_Notification_Sound_Path` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_lists`
--

CREATE TABLE `user_lists` (
  `List_ID` int(11) NOT NULL,
  `User_ID` int(11) DEFAULT NULL,
  `List_Name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `attachments_list`
--
ALTER TABLE `attachments_list`
  ADD PRIMARY KEY (`Attachment_ID`),
  ADD KEY `Task_ID` (`Task_ID`);

--
-- Indeksy dla tabeli `bills_list`
--
ALTER TABLE `bills_list`
  ADD PRIMARY KEY (`Bill_ID`),
  ADD KEY `Item_ID` (`Item_ID`);

--
-- Indeksy dla tabeli `items_list`
--
ALTER TABLE `items_list`
  ADD PRIMARY KEY (`Item_ID`),
  ADD KEY `List_ID` (`List_ID`);

--
-- Indeksy dla tabeli `registered_users`
--
ALTER TABLE `registered_users`
  ADD PRIMARY KEY (`User_ID`);

--
-- Indeksy dla tabeli `tasks_list`
--
ALTER TABLE `tasks_list`
  ADD PRIMARY KEY (`Task_ID`),
  ADD KEY `Item_ID` (`Item_ID`);

--
-- Indeksy dla tabeli `transfers_data`
--
ALTER TABLE `transfers_data`
  ADD PRIMARY KEY (`Transfer_ID`),
  ADD KEY `User_ID` (`User_ID`);

--
-- Indeksy dla tabeli `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`User_Id`);

--
-- Indeksy dla tabeli `users_preference`
--
ALTER TABLE `users_preference`
  ADD PRIMARY KEY (`User_preference_ID`),
  ADD KEY `User_ID` (`User_ID`);

--
-- Indeksy dla tabeli `user_lists`
--
ALTER TABLE `user_lists`
  ADD PRIMARY KEY (`List_ID`),
  ADD KEY `User_ID` (`User_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `attachments_list`
--
ALTER TABLE `attachments_list`
  MODIFY `Attachment_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `bills_list`
--
ALTER TABLE `bills_list`
  MODIFY `Bill_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `items_list`
--
ALTER TABLE `items_list`
  MODIFY `Item_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT dla tabeli `registered_users`
--
ALTER TABLE `registered_users`
  MODIFY `User_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT dla tabeli `tasks_list`
--
ALTER TABLE `tasks_list`
  MODIFY `Task_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT dla tabeli `transfers_data`
--
ALTER TABLE `transfers_data`
  MODIFY `Transfer_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `User_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT dla tabeli `users_preference`
--
ALTER TABLE `users_preference`
  MODIFY `User_preference_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `user_lists`
--
ALTER TABLE `user_lists`
  MODIFY `List_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `attachments_list`
--
ALTER TABLE `attachments_list`
  ADD CONSTRAINT `attachments_list_ibfk_1` FOREIGN KEY (`Task_ID`) REFERENCES `tasks_list` (`Task_ID`);

--
-- Ograniczenia dla tabeli `bills_list`
--
ALTER TABLE `bills_list`
  ADD CONSTRAINT `bills_list_ibfk_1` FOREIGN KEY (`Item_ID`) REFERENCES `items_list` (`Item_ID`);

--
-- Ograniczenia dla tabeli `items_list`
--
ALTER TABLE `items_list`
  ADD CONSTRAINT `items_list_ibfk_1` FOREIGN KEY (`List_ID`) REFERENCES `user_lists` (`List_ID`);

--
-- Ograniczenia dla tabeli `tasks_list`
--
ALTER TABLE `tasks_list`
  ADD CONSTRAINT `tasks_list_ibfk_1` FOREIGN KEY (`Item_ID`) REFERENCES `items_list` (`Item_ID`);

--
-- Ograniczenia dla tabeli `transfers_data`
--
ALTER TABLE `transfers_data`
  ADD CONSTRAINT `transfers_data_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `registered_users` (`User_ID`);

--
-- Ograniczenia dla tabeli `users_preference`
--
ALTER TABLE `users_preference`
  ADD CONSTRAINT `users_preference_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `registered_users` (`User_ID`);

--
-- Ograniczenia dla tabeli `user_lists`
--
ALTER TABLE `user_lists`
  ADD CONSTRAINT `user_lists_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `registered_users` (`User_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
