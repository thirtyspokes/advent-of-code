defmodule Wrapping do
  
  def get_total_paper() do
    File.read("input.txt")
    |> elem(1)
    |> String.split("\n")
    |> Enum.map(&Wrapping.calculate_paper/1)
    |> Enum.map(&elem(&1, 1))
    |> Enum.filter(fn a -> a != "invalid format" end)
    |> Enum.sum
  end

  def get_total_ribbon() do
    File.read("input.txt")
    |> elem(1)
    |> String.split("\n")
    |> Enum.map(&Wrapping.calculate_ribbon/1)
    |> Enum.map(&elem(&1, 1))
    |> Enum.filter(fn a -> a != "invalid format" end)
    |> Enum.sum
  end

  @doc ~S"""
  Calculates the required amount of wrapping paper, given
  a string in the format 'LxWxH', for the length by the
  width by the height.

  From the string, the total amount of paper is calculated
  as 2*L + 2*W + 2*H, plus the area of the smallest side.

  ### Examples

      iex> Wrapping.calculate_paper "2x3x4"
      {:ok, 58}

      iex> Wrapping.calculate_paper "1x1x10"
      {:ok, 43}

      iex> Wrapping.calculate_paper "invalid"
      {:error, "invalid format"}
  """
  def calculate_paper(dimensions) do
    case String.split(dimensions, "x") do
      [length, width, height] ->
        {:ok, get_area([length, width, height])}
      _ -> 
        {:error, "invalid format"}
    end
  end

  def calculate_ribbon(dimensions) do
    case String.split(dimensions, "x") do
      [length, width, height] ->
        {:ok, get_ribbon(Enum.map([length, width, height], &to_integer/1))}
      _ ->
        {:error, "invalid format"}
    end
  end

  def get_area(dimensions) do
    [a, b, c] = Enum.map(dimensions, &to_integer(&1))
    (2*a*b) + (2*b*c) + (2*c*a) + get_slack([a,b,c])
  end

  @doc ~S"""
  Given a string, returns an integer.

  ### Examples
  
      iex> Wrapping.to_integer("3")
      3

      iex> Wrapping.to_integer("100")
      100
  """
  @spec to_integer(String.t()) :: integer
  def to_integer(elem) do
    elem |> Integer.parse |> elem(0)
  end
  
  @doc ~S"""
  The slack required to wrap a present is defined as the area
  its smallest side.

  ### Examples

      iex> Wrapping.get_slack [1, 1, 100]
      1

      iex> Wrapping.get_slack [3, 5, 4]
      12
  """
  @spec get_slack([integer]) :: integer
  def get_slack(dimensions) do
    [fst, snd] = dimensions |> Enum.sort |> Enum.take(2)
    fst * snd
  end

  @doc ~S"""
  Given a present with dimensions [a, b, c], the length
  of ribbon is the perimeter of the smallest face, or 
  a + a + b + b, where a and b are the smallest sides.

  In addition, to make the bow, we need an addition length
  of ribbon equal to the volume of the present, or a*b*c.

  ### Examples
  
      iex> Wrapping.get_ribbon([2,3,4])
      34

      iex> Wrapping.get_ribbon([1,1,10])
      14
  """
  @spec get_ribbon([integer]) :: integer
  def get_ribbon(dimensions) do
    [fst, snd] = dimensions |> Enum.sort |> Enum.take(2)
    fst*2 + snd*2 + Enum.reduce(dimensions, &*/2)
  end
end
