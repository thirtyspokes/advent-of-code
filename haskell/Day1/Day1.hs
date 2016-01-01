module Day1 where

day1Part1 :: [Char] -> Integer
day1Part1 = foldr ((+) . move) 0

day1Part2 :: [Char] -> Integer
day1Part2 steps = fst $ last $ takeWhile inBasement (indexedSteps steps)

move :: Char -> Integer
move '(' = 1
move ')' = (-1)
move _   = 0

inBasement :: (Integer, Integer) -> Bool
inBasement (_,y) = y > -2

indexedSteps :: [Char] -> [(Integer, Integer)]
indexedSteps xs = zip [1..] (reverse (foldl maybeAddMove [] xs))

maybeAddMove :: [Integer] -> Char -> [Integer]
maybeAddMove [] x = [move x]
maybeAddMove xs x = ((move x) + head xs) : xs
