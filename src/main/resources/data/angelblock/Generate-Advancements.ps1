# Generate-Advancements.ps1
$recipesPath = ".\recipe"
$advancementsPath = ".\advancement\recipes"
$modId = "angelblock"

Get-ChildItem -Path $recipesPath -Filter *.json -Recurse | ForEach-Object {
    $recipePath = $_.FullName
    $recipe = Get-Content $recipePath | ConvertFrom-Json

    $type = $recipe.type
    $ingredients = @()

    switch ($type) {
        "minecraft:crafting_shaped" {
            $key = $recipe.key
            foreach ($entry in $key.PSObject.Properties) {
                $value = $entry.Value
                if ($value -is [string]) {
                    $ingredients += $value
                } elseif ($value -is [hashtable]) {
                    $ingredients += $value.item
                }
            }
        }
        "minecraft:crafting_shapeless" {
            foreach ($ing in $recipe.ingredients) {
                if ($ing -is [string]) {
                    $ingredients += $ing
                } elseif ($ing -is [hashtable]) {
                    $ingredients += $ing.item
                }
            }
        }
        "minecraft:smelting" {
            $ing = $recipe.ingredient
            if ($ing -is [string]) {
                $ingredients += $ing
            } elseif ($ing -is [hashtable]) {
                $ingredients += $ing.item
            }
        }
        "minecraft:blasting" {
            $ing = $recipe.ingredient
            if ($ing -is [string]) {
                $ingredients += $ing
            } elseif ($ing -is [hashtable]) {
                $ingredients += $ing.item
            }
        }
        "minecraft:smithing_transform" {
            $ingredients += $recipe.base
            $ingredients += $recipe.addition
            $ingredients += $recipe.template
        }
        default {
            Write-Host "Unknown recipe type: $type"
            Write-Host "File: $recipePath"
            return
        }
    }

    $outputId = $recipe.result.id
    $ingredients += $outputId
    
    #$outputName = [System.IO.Path]::GetFileName($recipePath)
    $outputName = [System.IO.Path]::GetFileNameWithoutExtension($recipePath)
    $advPath = Join-Path $advancementsPath "$outputName.json"
    
    #Write-Host "Parsed file: $recipePath"

    $criteria = @{}
    for ($i = 0; $i -lt $ingredients.Count; $i++) {
        $criteria["item$i"] = @{
            "trigger" = "minecraft:inventory_changed"
            "conditions" = @{
                "items" = @(@{"items" = $ingredients[$i]})
            }
        }
    }

    $requirements = @()
    $requirements += ,$criteria.Keys
    
    $advancement = @{
        "parent" = "minecraft:recipes/root"
        "criteria" = $criteria
        "requirements" = @($requirements)
        "rewards" = @{
            "recipes" = @(
                "${modId}:${outputName}"
            )
        }
    }

    $json = $advancement | ConvertTo-Json -Depth 10
    $advDir = Split-Path $advPath
    if (!(Test-Path $advDir)) {
        New-Item -ItemType Directory -Path $advDir -Force | Out-Null
    }
    $json | Set-Content $advPath -Encoding UTF8
    Write-Host "Generated: $advPath"
}
