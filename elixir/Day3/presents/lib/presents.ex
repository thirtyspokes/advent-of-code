defmodule Presents do
  @doc ~S"""
  Santa, traveling along an infinite 2D grid, can be instructed to
  move either north (^), south (v), east (>), or west (<).  Given
  a sequence of instructions, we want to determine how many locations
  receive at leat one present.  The starting location always receives
  one present.

      iex> Presents.deliveries ">"
      2

      iex> Presents.deliveries "^>v<"
      4

      iex> Presents.deliveries "^v^v^v^v^v"
      2
  """
  @spec deliveries(binary()) :: non_neg_integer()
  def deliveries(input) do
    String.split(input, "")
    |> Enum.reduce([{0,0}], fn dir, loc -> [move(hd(loc), dir) | loc] end)
    |> Enum.uniq
    |> length
  end

  @doc ~S"""
  Calculates the number of homes receiving deliveries as in deliveries/1, 
  but in this case Santa and a Robo-Santa take turns moving, both starting at
  position 0, 0.  E.g., the move set ["^", "v"] will result in Santa moving to
  0, 1, followed by Robo-Santa moving to [0, -1].

      iex> Presents.team_deliveries("^v")
      3

      iex> Presents.team_deliveries("^>v<")
      3

      iex> Presents.team_deliveries("^v^v^v^v^v")
      11
  """
  @spec team_deliveries(binary()) :: non_neg_integer()
  def team_deliveries(input) do
    {santa, robosanta} = assign_moves(String.split(input, ""))
    santa_moves = Enum.reduce(santa, [{0,0}], fn dir, loc -> [move(hd(loc), dir) | loc] end)
    robo_moves = Enum.reduce(robosanta, [{0,0}], fn dir, loc -> [move(hd(loc), dir) | loc] end)

    santa_moves ++ robo_moves
    |> Enum.uniq
    |> length
  end

  @doc ~S"""
  Given a list of potential moves, partitions them by whether their
  index is odd or even.

      iex> Presents.assign_moves(["^", "v", "<", ">"])
      {["^", "<"], ["v", ">"]}

      iex> Presents.assign_moves(["^", "v", "^", "v", "^"])
      {["^", "^", "^"], ["v", "v"]}
  """
  @spec assign_moves([binary()]) :: {[binary()], [binary()]}
  def assign_moves(moves) do
    {evens, odds} = Enum.reduce(Enum.with_index(moves), {[], []}, &assign_item/2)
    {Enum.reverse(evens), Enum.reverse(odds)}
  end

  @doc ~S"""
  Given a tuple containing an item and its index position in some
  greater list, assigns that item to one of two lists depending on
  whether or not the item's index is odd or even, and returns a tuple
  in the format {evens, odds}.

  An index of 0 is considered even.
  
      iex> Presents.assign_item({"^", 0}, {[], []})
      {["^"], []}
  
      iex> Presents.assign_item({"^", 7}, {[], []})
      {[], ["^"]}

      iex> Presents.assign_item({"^", 8}, {[], []})
      {["^"], []}
  """
  @spec assign_item({binary(), integer()}, {binary(), binary()}) :: {[binary()], [binary()]}
  def assign_item({item, index}, {evens, odds}) do
    require Integer
    
    if Integer.is_odd(index) do
      {evens, [item | odds]}
    else
      {[item | evens], odds}
    end
  end

  @doc ~S"""
  Given a tuple containing the cartesian coordinates of Santa's
  current location and a 1-length string containing a valid direction,
  returns a new tuple representing Santa's location after moving
  one square.

      iex> Presents.move({0, 0}, ">")
      {1, 0}

      iex> Presents.move({0, 0}, "<")
      {-1, 0}

      iex> Presents.move({0, 0}, "^")
      {0, 1}

      iex> Presents.move({0, 0}, "v")
      {0, -1}

      iex> Presents.move({0, 0}, "!")
      {0, 0}
  """
  @spec move({integer, integer}, String.t()) :: {integer, integer}
  def move({x, y}, direction) do
    case direction do
      ">" -> {x + 1, y}
      "<" -> {x - 1, y}
      "^" -> {x, y + 1}
      "v" -> {x, y - 1}
      _ -> {x, y}
    end
  end
end
