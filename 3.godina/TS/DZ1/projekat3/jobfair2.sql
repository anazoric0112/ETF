-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2021 at 11:04 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jobfair2`
--

-- --------------------------------------------------------

--
-- Table structure for table `biografije`
--

CREATE TABLE `biografije` (
  `korime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `ime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `adresa` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `postanskiBroj` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `grad` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `drzava` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `telefon` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `mejl` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sajt` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `pol` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `datumrodj` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `nacionalnost` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `zeljpozicija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `fakultet` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `izjava` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `mjezik` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `kvestine` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `ovestine` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `mvestine` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dvestine` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `biografije`
--

INSERT INTO `biografije` (`korime`, `ime`, `prezime`, `adresa`, `postanskiBroj`, `grad`, `drzava`, `telefon`, `mejl`, `sajt`, `pol`, `datumrodj`, `nacionalnost`, `zeljpozicija`, `fakultet`, `izjava`, `mjezik`, `kvestine`, `ovestine`, `mvestine`, `dvestine`) VALUES
('aleksa', 'Aleksa', 'Aleksic', 'Petra Martinovica 26', '11000', 'Beograd', 'Srbija', '0651111111', 'aki@gmail.com', 'aki.rs', 'M', '1997-12-10', 'srpska', 'bilo koja', 'ETF', 'vredan, radan, dobar timski igrac', 'srpski', 'vrlo dobre', 'odlicne', 'odlicne', 'voznja'),
('micika', 'Milica', 'Vlasonjic', 'Ulica sljunka 1a', '12000', 'Gornji Milanovac', 'Srbija', '0651111111', 'mica@gmail.com', 'mica.rs', 'M', '1996-08-15', 'srpska', 'softverski inzinjer', 'FTN', '', 'srpski', 'odlicne', 'odlicne', 'odlicne', ''),
('miki8888', 'Milos', 'Gvozdenovic', 'Molerova 39', '11000', 'Beograd', 'Srbija', '0645454545', 'miki@gmail.com', 'miki.com', 'M', '1996-04-01', 'srpska', 'menadzer', 'ETF', '', 'srpski', 'odlicne', 'odlicne', 'odlicne', '');

-- --------------------------------------------------------

--
-- Table structure for table `dodatneusluge`
--

CREATE TABLE `dodatneusluge` (
  `id` int(11) NOT NULL,
  `naziv` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `cena` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `prezentacija` tinyint(4) NOT NULL,
  `sajam` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `dodatneusluge`
--

INSERT INTO `dodatneusluge` (`id`, `naziv`, `cena`, `prezentacija`, `sajam`) VALUES
(9, 'Prezentacija', '5000', 1, 53),
(10, 'Flajeri', '1500', 0, 53),
(11, 'Brendiranje standa', '10000', 0, 53),
(0, '', '', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `intervalcv`
--

CREATE TABLE `intervalcv` (
  `id` int(11) NOT NULL,
  `oddatum` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `odvreme` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dodatum` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dovreme` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `intervalcv`
--

INSERT INTO `intervalcv` (`id`, `oddatum`, `odvreme`, `dodatum`, `dovreme`) VALUES
(3, '2019-01-23', '13:00', '2019-01-28', '14:00'),
(4, '2019-01-01', '14:14', '2019-01-09', '14:00');

-- --------------------------------------------------------

--
-- Table structure for table `intervaljf`
--

CREATE TABLE `intervaljf` (
  `id` int(11) NOT NULL,
  `datumod` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `datumdo` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vremeod` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vremedo` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `intervaljf`
--

INSERT INTO `intervaljf` (`id`, `datumod`, `datumdo`, `vremeod`, `vremedo`) VALUES
(2, '2019-01-23', '2019-01-28', '14:00', '14:00'),
(3, '2019-01-08', '2019-01-15', '14:00', '14:00'),
(0, '2021-12-15', '2021-12-20', '02:01', '02:02');

-- --------------------------------------------------------

--
-- Table structure for table `jezici`
--

CREATE TABLE `jezici` (
  `id` int(11) NOT NULL,
  `razumevanje` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `citanje` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `pricanje` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `pisanje` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `korime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `jezik` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `jezici`
--

INSERT INTO `jezici` (`id`, `razumevanje`, `citanje`, `pricanje`, `pisanje`, `korime`, `jezik`) VALUES
(5, 'B2', 'B2', 'B2', 'B1', 'aleksa', 'engleski'),
(6, 'A1', 'A1', 'A1', 'A1', 'aleksa', 'nemacki'),
(7, 'C1', 'B2', 'B1', 'C1', 'micika', 'engleski'),
(8, 'B2', 'B1', 'A2', 'B1', 'micika', 'francuski'),
(9, 'B2', 'B2', 'B2', 'B2', 'miki8888', 'engleski'),
(10, 'A1', 'A1', 'A1', 'A1', 'miki8888', 'nemacki');

-- --------------------------------------------------------

--
-- Table structure for table `kompanijahocedodatneusluge`
--

CREATE TABLE `kompanijahocedodatneusluge` (
  `id` int(11) NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `usluga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `kompanijahocedodatneusluge`
--

INSERT INTO `kompanijahocedodatneusluge` (`id`, `kompanija`, `usluga`) VALUES
(17, 'mikazika', 9),
(18, 'mikazika', 11),
(19, 'aperitiv', 9);

-- --------------------------------------------------------

--
-- Table structure for table `kompanijahocepaket`
--

CREATE TABLE `kompanijahocepaket` (
  `id` int(11) NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `paket` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `kompanijahocepaket`
--

INSERT INTO `kompanijahocepaket` (`id`, `kompanija`, `paket`) VALUES
(12, 'mikazika', 9),
(13, 'aperitiv', 11),
(14, 'pericadj', 9);

-- --------------------------------------------------------

--
-- Table structure for table `kompanije`
--

CREATE TABLE `kompanije` (
  `korime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `naziv` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `grad` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `adresa` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `PIB` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `brzaposlenih` int(11) NOT NULL,
  `email` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sajt` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `delatnost` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `specijalnost` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `logo` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `kompanije`
--

INSERT INTO `kompanije` (`korime`, `naziv`, `grad`, `adresa`, `PIB`, `brzaposlenih`, `email`, `sajt`, `delatnost`, `specijalnost`, `logo`) VALUES
('aperitiv', 'aperitivdoo', 'Novi Sad', 'Miletica 12', '33', 20, 'aperitiv@gmail.com', 'aperitiv.com', 'Energentika', 'Vozila', 0x736c696b61312e6a7067),
('mikazika', 'Mika zika', 'Beograd', 'Beogradska 12', '1121', 100, 'mika@gmail.com', 'mika.com', 'IT', 'Mreze', 0x736c696b61312e6a7067),
('milivoje', 'Vatrogasci', 'Beograd', 'Molerova 39', '2345', 10, 'mili@gmail.com', 'mili.rs', 'IT', 'Baze podataka', 0x736c696b61312e6a7067),
('pericadj', 'Djekan', 'Bukurest', 'Bulevar Oslobodjenja 32', '1121', 230, 'djeka@gmail.com', 'djeka.org', 'Gradjevina', 'Geodezija', 0x736c696b61312e6a7067);

-- --------------------------------------------------------

--
-- Table structure for table `konkursi`
--

CREATE TABLE `konkursi` (
  `id` int(11) NOT NULL,
  `korime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `naziv` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `tekstK` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `datum` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vreme` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `posao` tinyint(4) NOT NULL,
  `praksa` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `konkursi`
--

INSERT INTO `konkursi` (`id`, `korime`, `naziv`, `tekstK`, `datum`, `vreme`, `posao`, `praksa`) VALUES
(5, 'mikazika', 'konkurs1', 'Bas je super posao', '2019-01-22', '13:00', 1, 1),
(6, 'mikazika', 'konkurs2', 'so ql', '2019-01-29', '13:00', 0, 1),
(7, 'mikazika', 'konkurs3', 'so so ql', '2019-01-22', '13:00', 0, 1),
(8, 'aperitiv', 'kul konkurs', 'Trazimo inzinjere masinstva', '2019-01-22', '13:00', 1, 0),
(9, 'aperitiv', 'kul konkurs2', 'Trazimo inzinjere masinstva', '2019-01-28', '13:00', 0, 1),
(10, 'pericadj', 'djekan konkurs', 'Konkurs', '2019-01-22', '13:00', 1, 1),
(11, 'mikazika', 'konkurs4', 'so so so cool', '2019-01-22', '12:15', 1, 1),
(0, 'pericadj', 'Novi konkurs', 'Ovo je novi konkurs', '2021-12-18', '00:00', 1, 1),
(0, 'pericadj', 'Novi konkurs 2', 'Ovo je konkurs za praksu', '2021-12-16', '00:00', 0, 1),
(0, 'pericadj', 'Novi konkurs 3', 'Strucna praksa', '2021-12-17', '00:00', 0, 1),
(0, 'aperitiv', 'Moj konkurs', 'Strucne prakse i poslovi!', '2021-12-23', '20:32', 1, 1),
(0, 'aperitiv', 'Paznja!!', 'Zaposlite se', '2021-12-19', '08:36', 1, 0),
(0, 'mikazika', 'Jos jedan konkurs', 'I jos jedan tekst', '2021-12-25', '22:59', 1, 1),
(0, 'pericadj', 'Konkurs 200', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 333', 'stagod', '2021-12-08', '16:33', 0, 1),
(0, 'pericadj', 'Konkurs 101', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 101', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 101', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs za praksu', 'Dodjite', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'hvjh', '', '2022-01-07', '08:59', 1, 0),
(0, 'pericadj', 'Novi konkursjvh', 'kjb', '2021-12-02', '18:03', 0, 1),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs za prasku', 'Dodjite', '2021-12-25', '00:00', 0, 1),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'NestoKonkursi 1', 'Nesto', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'NestoKonkursi 1', 'Nesto', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs za praksu', 'Dodjite', '2021-12-25', '00:00', 0, 1),
(0, 'pericadj', 'Poslovi i prakse', 'Opis posla', '2021-12-31', '00:00', 1, 1),
(0, 'pericadj', 'NestoKonkursi 1', 'Nesto', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'NestoKonkursi 1', 'Nesto', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Nesto', 'NestoZaposlite se', '2021-12-27', '00:00', 1, 0),
(0, 'pericadj', 'Konkursi 1', '', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Nesto', 'NestoZaposlite se', '2021-12-27', '00:00', 1, 0),
(0, 'pericadj', 'Konkursi 1', '', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Nesto', 'NestoZaposlite se', '2021-12-27', '00:00', 1, 0),
(0, 'pericadj', 'Konkursi 1', '', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Nesto', 'NestoZaposlite se', '2021-12-27', '00:00', 1, 0),
(0, 'pericadj', 'Konkursi 1', '', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Nesto', 'NestoZaposlite se', '2021-12-27', '00:00', 1, 0),
(0, 'pericadj', 'Konkursi 1', '', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Nesto', 'NestoZaposlite se', '2021-12-27', '00:00', 1, 0),
(0, 'pericadj', 'Konkursi 1', '', '2021-12-20', '00:00', 1, 0),
(0, 'pericadj', 'Novo', 'Prijavite se za posao', '2021-11-11', '11:00', 1, 0),
(0, 'pericadj', 'Konkurs 100', 'Prijavite se', '2021-12-25', '00:00', 1, 0),
(0, 'pericadj', 'Konkurs za praksu', 'Dodjite', '2021-12-25', '00:00', 0, 1),
(0, 'pericadj', 'Poslovi i prakse', 'Opis posla', '2021-12-31', '00:00', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `korisnickoime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `tip` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`korisnickoime`, `password`, `tip`) VALUES
('aleksa', '8cc5af5f7516e429ff3c135e85eafc73', 1),
('aperitiv', 'd1021ceca50310bb98761d45710ea5fb', 2),
('bule', '8cc5af5f7516e429ff3c135e85eafc73', 1),
('jeca', '8cc5af5f7516e429ff3c135e85eafc73', 1),
('micika', '8cc5af5f7516e429ff3c135e85eafc73', 1),
('mikazika', 'd1021ceca50310bb98761d45710ea5fb', 2),
('miki8888', '8cc5af5f7516e429ff3c135e85eafc73', 1),
('milivoje', 'd1021ceca50310bb98761d45710ea5fb', 2),
('pericadj', 'd1021ceca50310bb98761d45710ea5fb', 2),
('stevica', 'e714f5e09b26f37bb36f63f24789a3b5', 3),
('tviti', 'e714f5e09b26f37bb36f63f24789a3b5', 3),
('anaz01', 'a7afe403314053431ee199a9e3c8e7bc', 1),
('anazzzzzzzzz', '430b3ab221b5392bb9a795b0f37a6851', 1),
('anazzzzzzzzzz', '9a44cdaf6f25318c0c024d3f358f7795', 1),
('anazorich', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('anazz01', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('neznamvise', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('neznamvise1', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('nezna1', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('neznam1', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('kukum01', 'e2ea2bc2c072078c8ae417491cf29be7', 1),
('korime123', '26697e6635d893eafce81de1c9ae8e28', 1),
('odakle124', '2175fac0bd4e08109b7a7f7bbb0d3392', 1),
('odakl124', '2175fac0bd4e08109b7a7f7bbb0d3392', 1),
('korime1234', 'f95770faab5da94b9b1bef45a610a2f9', 1),
('neznam0123', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznamm0123', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam133', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam134', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam141', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam143', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam144', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam147', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam148', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam149', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam150', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1433', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1444', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1477', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1488', 'ae94e5ff7c352ce4cd9277cfb5e22ca5', 1),
('neznam1377', '75d9404e1422b0c6faa49d62bbf7e89f', 1),
('neznam01231', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam012311', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam0124', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam01241', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam01242', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznamm0124', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznamm01241', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1321', 'adc0d369688e71e3cd3fee7af3d25f7d', 1),
('neznam1322', 'adc0d369688e71e3cd3fee7af3d25f7d', 1),
('neznam1331', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1332', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1341', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1342', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('nekoime', 'a8e9cfc6851040b84816e2b1fa99b3d5', 1),
('neznam1361', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1371', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1391', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1401', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1362', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1363', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1364', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1360', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1374', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1375', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1393', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1394', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1376', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1395', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1403', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1404', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('anazoricheeeeee', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1412', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1413', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1432', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1435', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1443', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1445', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1473', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1474', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1483', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1484', '4f1b975ec7c35c20e901e8a1064a8980', 1),
('neznam1251', '278c296cc1ee045de239c5e445bc49dc', 1),
('neznam1281', '4308acbe0dbd3c22fe54452ebf4e6ae5', 1),
('neznam12812', '4308acbe0dbd3c22fe54452ebf4e6ae5', 1),
('neznam1291', '14eee4a9c9fb5f73b75519d787a67406', 1),
('neznam12345', '278c296cc1ee045de239c5e445bc49dc', 1);

-- --------------------------------------------------------

--
-- Table structure for table `obrazovanje`
--

CREATE TABLE `obrazovanje` (
  `id` int(11) NOT NULL,
  `korime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `od` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `do` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `titula` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `fakultet` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `grad` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `drzava` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `obrazovanje`
--

INSERT INTO `obrazovanje` (`id`, `korime`, `od`, `do`, `titula`, `fakultet`, `grad`, `drzava`) VALUES
(3, 'aleksa', '2015-09-16', '2019-01-23', 'Dipl ing elektrotehnike', 'ETF', 'Beograd', 'Srbija'),
(4, 'aleksa', '2011-09-01', '2015-06-16', 'zavrsio srednju', 'Matematicka gimnazija', 'Beograd', 'Srbija'),
(5, 'micika', '2015-03-04', '2019-01-23', 'inzinjer elektrotehnike', 'FTN', 'Novi Sad', 'Srbija'),
(6, 'micika', '2010-01-13', '2014-09-01', 'zavrsila srednju', 'Matematicka gimnazija', 'Beograd', 'Srbija'),
(7, 'miki8888', '1996-01-02', '2019-01-23', 'master', 'zivotni fakultet', 'Beograd', 'Banovo Brdo republika');

-- --------------------------------------------------------

--
-- Table structure for table `ocene`
--

CREATE TABLE `ocene` (
  `id` int(11) NOT NULL,
  `student` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `ocena` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `ocene`
--

INSERT INTO `ocene` (`id`, `student`, `kompanija`, `ocena`) VALUES
(2, 'miki8888', 'aperitiv', 6),
(3, 'aleksa', 'mikazika', 9);

-- --------------------------------------------------------

--
-- Table structure for table `paketi`
--

CREATE TABLE `paketi` (
  `id` int(11) NOT NULL,
  `naziv` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `velicinastanda` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `logoBrosure` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `velicinalogoa` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `minpromocije` int(11) NOT NULL,
  `brpredavanja` int(11) NOT NULL,
  `brradionica` int(11) NOT NULL,
  `cena` int(11) NOT NULL,
  `maxkupovanja` int(11) NOT NULL,
  `sajam` int(11) NOT NULL,
  `brkupovanja` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `paketi`
--

INSERT INTO `paketi` (`id`, `naziv`, `velicinastanda`, `logoBrosure`, `velicinalogoa`, `minpromocije`, `brpredavanja`, `brradionica`, `cena`, `maxkupovanja`, `sajam`, `brkupovanja`) VALUES
(8, 'paket1', 'cetvorostruke', 'Logo i brosure u boji', 'dvostruke', 10, 2, 2, 25000, 3, 53, 0),
(9, 'paket2', 'dvostruke', 'Logo crno-beli', 'dvostruke', 5, 1, 0, 15000, 2, 53, 4),
(10, 'paket3', 'jednostruke', 'Logo crno-beli', 'standardne', 0, 1, 0, 10000, 25, 53, 0),
(11, 'paket4', 'cetvorostruke', 'Logo i brosure u boji', 'trostruke', 30, 2, 2, 35000, 2, 53, 1),
(0, 'Zlatni', 'cetvorostruke', 'ukljucene', 'trostruke', 10, 10, 1, 2000, 1, 0, 0),
(0, 'srebrni', 'dvostruke', 'ukljucene', 'trostruke', 3, 3, 1, 1500, 4, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `predavanja`
--

CREATE TABLE `predavanja` (
  `id` int(11) NOT NULL,
  `vremepocetka` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vremekraja` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dodeljena` tinyint(4) NOT NULL,
  `prostorija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sajam` int(11) NOT NULL,
  `datum` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `predavanja`
--

INSERT INTO `predavanja` (`id`, `vremepocetka`, `vremekraja`, `dodeljena`, `prostorija`, `sajam`, `datum`, `kompanija`) VALUES
(16, '14:00', '15:00', 1, 'prostorija2', 53, '2019-01-28', 'mikazika'),
(17, '13:00', '15:00', 0, 'prostorija2', 53, '2019-01-29', ''),
(18, '13:00', '16:00', 0, 'prostorija2', 53, '2019-01-30', '');

-- --------------------------------------------------------

--
-- Table structure for table `prezentacije`
--

CREATE TABLE `prezentacije` (
  `id` int(11) NOT NULL,
  `vremepocetka` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vremekraja` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dodeljena` tinyint(4) NOT NULL,
  `prostorija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sajam` int(11) NOT NULL,
  `datum` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `prezentacije`
--

INSERT INTO `prezentacije` (`id`, `vremepocetka`, `vremekraja`, `dodeljena`, `prostorija`, `sajam`, `datum`, `kompanija`) VALUES
(4, '13:00', '14:00', 1, 'prostorija2', 53, '2019-01-28', 'mikazika'),
(5, '16:00', '17:00', 0, 'prostorija2', 53, '2019-01-28', ''),
(6, '16:00', '17:00', 0, 'prostorija2', 53, '2019-01-29', '');

-- --------------------------------------------------------

--
-- Table structure for table `prijavajf`
--

CREATE TABLE `prijavajf` (
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sajam` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `odobreno` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `prijavajf`
--

INSERT INTO `prijavajf` (`kompanija`, `sajam`, `id`, `odobreno`) VALUES
('mikazika', 53, 21, 1),
('aperitiv', 53, 22, 0),
('pericadj', 53, 23, -1);

-- --------------------------------------------------------

--
-- Table structure for table `prijavakonkurs`
--

CREATE TABLE `prijavakonkurs` (
  `id` int(11) NOT NULL,
  `student` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `konkurs` int(11) NOT NULL,
  `prihvacen` tinyint(4) NOT NULL,
  `cover` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `prijavakonkurs`
--

INSERT INTO `prijavakonkurs` (`id`, `student`, `konkurs`, `prihvacen`, `cover`) VALUES
(7, 'aleksa', 11, -1, ''),
(8, 'aleksa', 9, 0, ''),
(9, 'aleksa', 5, 1, ''),
(10, 'aleksa', 8, 1, ''),
(11, 'aleksa', 6, 0, ''),
(12, 'micika', 5, -1, ''),
(13, 'micika', 6, 0, ''),
(14, 'micika', 9, 0, ''),
(15, 'micika', 11, -1, ''),
(16, 'micika', 10, 1, ''),
(17, 'miki8888', 5, 1, ''),
(18, 'miki8888', 7, -1, ''),
(19, 'miki8888', 9, 0, ''),
(20, 'miki8888', 11, 1, ''),
(0, 'aleksa', 0, 0, 'Zelim se prijaviti'),
(0, 'micika', 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `radi`
--

CREATE TABLE `radi` (
  `student` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `od` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `radi`
--

INSERT INTO `radi` (`student`, `kompanija`, `od`, `id`) VALUES
('aleksa', 'mikazika', '2018-01-23', 3),
('aleksa', 'aperitiv', '2019-01-23', 4),
('miki8888', 'aperitiv', '2018-01-23', 5);

-- --------------------------------------------------------

--
-- Table structure for table `radionice`
--

CREATE TABLE `radionice` (
  `id` int(11) NOT NULL,
  `datum` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vremepocetka` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `vremekraja` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dodeljena` tinyint(4) NOT NULL,
  `prostorija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sajam` int(11) NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `radionice`
--

INSERT INTO `radionice` (`id`, `datum`, `vremepocetka`, `vremekraja`, `dodeljena`, `prostorija`, `sajam`, `kompanija`) VALUES
(24, '2019-01-28', '15:00', '16:00', 0, 'prostorija2', 53, ''),
(25, '2019-01-29', '13:00', '16:00', 0, 'prostorija1', 53, ''),
(26, '2019-01-29', '16:00', '18:00', 0, 'prostorija1', 53, ''),
(27, '2019-01-29', '17:00', '18:00', 0, 'prostorija2', 53, '');

-- --------------------------------------------------------

--
-- Table structure for table `radnoiskustvo`
--

CREATE TABLE `radnoiskustvo` (
  `id` int(11) NOT NULL,
  `korime` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `od` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `do` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `pozicija` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `kompanija` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `grad` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `drzava` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `radnoiskustvo`
--

INSERT INTO `radnoiskustvo` (`id`, `korime`, `od`, `do`, `pozicija`, `kompanija`, `grad`, `drzava`) VALUES
(6, 'aleksa', '2017-12-05', '2018-12-05', 'konsultant', 'Mika zika', 'Beograd', 'Srbija'),
(7, 'aleksa', '2019-01-01', '2019-01-15', 'pomocnik pomocnika', 'Nikita', 'Vrsac', 'Srbija'),
(8, 'micika', '2013-01-24', '2017-01-04', 'softverski inzinjer', 'Microsoft', 'Beograd', 'Srbija'),
(9, 'miki8888', '2018-07-01', '2018-10-01', 'sekretar', 'Svetlost', 'Beograd', 'Srbija');

-- --------------------------------------------------------

--
-- Table structure for table `sajam`
--

CREATE TABLE `sajam` (
  `id` int(11) NOT NULL,
  `naziv` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `datumod` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `datumdo` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `logo` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `sajam`
--

INSERT INTO `sajam` (`id`, `naziv`, `datumod`, `datumdo`, `logo`) VALUES
(0, 'sajamPoslova', '2022-01-01', '2022-01-07', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
